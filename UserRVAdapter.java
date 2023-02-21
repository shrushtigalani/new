package com.example.user;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayInputStream;
import java.util.List;

public class UserRVAdapter extends RecyclerView.Adapter<UserRVAdapter.ViewHolder> {

    List<com.example.user.userModelClass> userarraylist;
        Context mContext;
        com.example.user.DatabaseHelperClass databaseHelperClass;

    public UserRVAdapter(List<com.example.user.userModelClass> userarraylist, Context context) {
        this.userarraylist = userarraylist;
        this.mContext = context;
        databaseHelperClass = new com.example.user.DatabaseHelperClass(mContext);
    }
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.showdata_value,parent,false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
             com.example.user.userModelClass userModelClass = userarraylist.get(position);
            holder.id.setText(""+userModelClass.getId());
            holder.rfname.setText("First Name : "+userModelClass.getFname());
            holder.rlname.setText("Last Name :"+userModelClass.getLname());
            holder.recivemail.setText("Email ID : "+userModelClass.getEmail());
            holder.rnumber.setText("Mobile Number : "+userModelClass.getNumber());
            holder.recivegender.setText("Gender : "+userModelClass.getGender());
            holder.recivehobby.setText("Hobby : "+userModelClass.getHobby());
            holder.recivecounty.setText("Country : "+userModelClass.getData());
            holder.rdate.setText("Birth Date : "+userModelClass.getDate());
            ByteArrayInputStream imageStream = new ByteArrayInputStream(userModelClass.getImage());
            Bitmap theImage= BitmapFactory.decodeStream(imageStream);
            holder.image_show_user.setImageBitmap(theImage);


            holder.button_delete.setOnClickListener(v -> {
                databaseHelperClass.deleteuser(userModelClass.getId());
                userarraylist.remove(position);
                notifyDataSetChanged();
            });
            holder.button_Edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(mContext, update_data_activity.class);
                    i.putExtra("Id", userModelClass.getId());
                    i.putExtra("First Name", userModelClass.getFname());
                    i.putExtra("Last Name",userModelClass.getLname() );
                    i.putExtra("Email ID",userModelClass.getEmail() );
                    i.putExtra("Mobile Number",userModelClass.getNumber());
                    i.putExtra("Gender",userModelClass.getGender());
                    i.putExtra("Hobby",userModelClass.getHobby());
                    i.putExtra("Country",userModelClass.getData());
                    i.putExtra("Birth Date",userModelClass.getDate());
                    i.putExtra("bmp",userModelClass.getImage());
                   mContext.startActivity(i);
                }
            });

        }

    @Override
    public int getItemCount() {
        return userarraylist.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView rfname, rlname, recivemail, recivegender, recivehobby, recivecounty, rnumber, rdate,id;
        Button button_delete,button_Edit;
        ImageView image_show_user;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rfname=itemView.findViewById(R.id.rfname);
            rlname=itemView.findViewById(R.id.rlname);
            recivemail=itemView.findViewById(R.id.recivemail);
            rnumber=itemView.findViewById(R.id.rnumber);
            recivegender=itemView.findViewById(R.id.recivegender);
            recivehobby=itemView.findViewById(R.id.recivehobby);
            recivecounty=itemView.findViewById(R.id.recivecounty);
            rdate=itemView.findViewById(R.id.rdate);
            button_delete=itemView.findViewById(R.id.button_delete);
            button_Edit=itemView.findViewById(R.id.button_Edit);
            id=itemView.findViewById(R.id.id);
            image_show_user=itemView.findViewById(R.id.image_show_user);

        }
    }
}
