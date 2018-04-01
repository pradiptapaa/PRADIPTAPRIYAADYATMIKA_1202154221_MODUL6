package pradiptapa.latihan.pradiptapriyaadyatmika_1202154221_modul6;

/**
 * Created by monoc on 4/1/2018.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.MyViewHolder> {
    //deklarasi variable
    private List<CommentModelData> commentList;

    //class viewholder untuk declare dan inisialisasi views pada row yang digunakan
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName, tvComment;
        public ImageView imgAvatar;

        public MyViewHolder(View view) {
            super(view);
            imgAvatar = (ImageView) view.findViewById(R.id.imgAvatar);
            tvName = (TextView) view.findViewById(R.id.tvNama);
            tvComment = (TextView) view.findViewById(R.id.tvKomentar);
        }
    }

    //konstruktor untuk menerima data yang dikirimkan dari activity ke adapter
    public CommentAdapter(List<CommentModelData> commentList) {
        this.commentList = commentList;
    }

    //create ke layout row yang dipilih
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_comment_item, parent, false);

        return new MyViewHolder(itemView);
    }

    //binding antara data yang didapatkan ke dalam views
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        CommentModelData comment = commentList.get(position);
        holder.tvComment.setText(comment.getComment());
        holder.tvName.setText(comment.getName());
    }

    //count data
    @Override
    public int getItemCount() {
        return commentList.size();
    }
}