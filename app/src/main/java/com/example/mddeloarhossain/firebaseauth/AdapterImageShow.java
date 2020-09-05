package com.example.mddeloarhossain.firebaseauth;
        import android.content.Context;
        import android.support.annotation.NonNull;
        import android.support.v7.widget.RecyclerView;
        import android.view.ContextMenu;
        import android.view.LayoutInflater;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Filter;
        import android.widget.Filterable;
        import android.widget.ImageView;
        import android.widget.TextView;

        import com.squareup.picasso.Picasso;

        import java.util.ArrayList;
        import java.util.List;

/**
 * Created by MD. DELOAR HOSSAIN on 01-May-19.
 */

public class AdapterImageShow extends RecyclerView.Adapter<AdapterImageShow.MyViewHolder> {

    //****For Filtering here we use " implements Filterable " ****//
    private Context context;
    private List<AdapterImageUpload> uploadList;
    private List<AdapterImageUpload> uploadList1;//****For Filtering ****//
    private OnItemClickListener listener;

    public AdapterImageShow(Context context, List<AdapterImageUpload> uploadList) {
        this.context = context;
        this.uploadList = uploadList;
        uploadList1 = new ArrayList<>(uploadList);//****For Filtering****//
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.activity_image_upload, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        AdapterImageUpload uploadWithDetails = uploadList.get(position);
        Picasso.with(context)
                .load(uploadWithDetails.getImageUrl())
                .placeholder(R.drawable.question1)
                .fit()
                .centerCrop()
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return uploadList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {

        TextView name, location, city, bloodgroup;
        ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewId);
            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View view) {

            if(listener != null){
                int position = getAdapterPosition();

                if(position != RecyclerView.NO_POSITION){

                    listener.OnItemClick(position);
                }

            }

        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {

            contextMenu.setHeaderTitle("Choose an action");
            MenuItem doAnyTask = contextMenu.add(Menu.NONE, 1, 1, "do any task");
            MenuItem delete = contextMenu.add(Menu.NONE, 2, 2, "delete");

            doAnyTask.setOnMenuItemClickListener(this);
            delete.setOnMenuItemClickListener(this);

        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {

            if(listener != null){
                int position = getAdapterPosition();

                if(position != RecyclerView.NO_POSITION){

                    switch (menuItem.getItemId()){

                        case 1:
                            listener.onDoAnyTask(position);
                            return true;


                        case 2:
                            listener.onDelete(position);
                            return true;



                    }
                }

            }


            return false;
        }
    }

    public interface OnItemClickListener{
        void OnItemClick(int position);
        void onDoAnyTask(int position);
        void onDelete(int position);

    }


    public  void setOnItemClickListener(OnItemClickListener listener){

        this.listener = listener;

    }


}




