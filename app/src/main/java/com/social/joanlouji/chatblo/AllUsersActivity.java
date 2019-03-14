package com.social.joanlouji.chatblo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class AllUsersActivity extends AppCompatActivity {
    Toolbar mtoolnar;
    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_users);
        mtoolnar=findViewById(R.id.alluserstoolbar);
        setSupportActionBar(mtoolnar);
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("Users");
        getSupportActionBar().setTitle("BloChat");
        mtoolnar.setTitleTextColor(getResources().getColor(android.R.color.white));
        user=FirebaseAuth.getInstance().getCurrentUser();
        recyclerView=findViewById(R.id.userlist);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));


    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Users,UserViewHolder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Users, UserViewHolder>(
                Users.class,
                R.layout.users_single_layout,
                UserViewHolder.class,
                databaseReference
        ) {
            @Override
            protected void populateViewHolder(UserViewHolder viewHolder, Users model, final int position) {
                viewHolder.setName(model.getName());
                viewHolder.setStatus(model.getStatus());
                viewHolder.setImage(model.getImage(),getApplicationContext());
                viewHolder.mview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String user_id=getRef(position).getKey();
                        Intent i=new Intent(AllUsersActivity.this,ProfileActivity.class);
                        i.putExtra("user_id",user_id);
                        startActivity(i);
                    }
                });

            }
        };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }
    public static class UserViewHolder extends RecyclerView.ViewHolder{
        View mview;
        public UserViewHolder(View itemView) {
            super(itemView);
            mview=itemView;
        }
        public void setName(String Name){
            TextView username=mview.findViewById(R.id.user_single_name);
            username.setText(Name);
        }
        public void setStatus(String statis){
            TextView status=mview.findViewById(R.id.user_single_status);
            status.setText(statis);
        }


        public void setImage(String image,Context ctx) {
            CircleImageView images=mview.findViewById(R.id.user_single_image);
            Picasso.with(ctx).load(image).into(images);
        }
    }

}
