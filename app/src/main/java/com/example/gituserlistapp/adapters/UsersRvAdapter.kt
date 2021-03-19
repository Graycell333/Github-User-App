package com.example.gituserlistapp.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.gituserlistapp.R
import com.example.gituserlistapp.models.UserList
import com.example.gituserlistapp.roomdb.Note

class UsersRvAdapter(var mCallBack: OnClickItemCallBack) :
    RecyclerView.Adapter<UsersRvAdapter.MyHolder>() {

    lateinit var mContext: Context
    private lateinit var allNotes: List<Note>
    var dataLeeds = arrayListOf<UserList>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersRvAdapter.MyHolder {

        mContext = parent.context
        return MyHolder(
            LayoutInflater.from(mContext)
                .inflate(R.layout.item_users, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return if (dataLeeds?.size != null) dataLeeds?.size as Int else 0
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        var leeds: UserList
        var note: Note

        if (dataLeeds != null && dataLeeds.size > 0) {
            leeds = dataLeeds.get(position)

            if (allNotes != null && allNotes.size > 0) {
                try {
                    for (not in allNotes) {
                        if (leeds.id.toInt() == not.id) {
                            holder.imageView.visibility = View.VISIBLE
                        }
                    }
                    Log.e("NoteIdOkay", "" + leeds.id.toInt())
                    /* if (note.userID.equals(leeds.id.toInt())) {
                         holder.imageView.visibility = View.VISIBLE
                     }*/
                    /* if (note.userID == leeds.id.toInt()) {
                         Log.e("UserId", "" + note.userID)
                         Log.e("NoteId", "" + leeds.id.toInt())
                         holder.imageView.visibility = View.VISIBLE
                     } else {
                         holder.imageView.visibility = View.GONE
                     }*/
                } catch (e: IndexOutOfBoundsException) {
                    Log.e("Exception", "Excepton")
                }


            }

            holder.headingText.text = leeds.login
            Glide.with(mContext).load(leeds.avatar_url).apply(RequestOptions.circleCropTransform())
                .into(holder.profileImageView);
            holder.detailText.text = "details"
            holder.detailText.setOnClickListener {
                mCallBack.onItemClick(position, leeds)
            }
        }


    }

    fun update(
        data: ArrayList<UserList>,
        allNotes: List<Note>
    ) {
        if (data != null) {
            this.dataLeeds = data
            this.allNotes = allNotes
            notifyDataSetChanged()
        }

    }

    fun addItems(
        data: ArrayList<UserList>,
        allNotes: List<Note>
    ) {
        dataLeeds.addAll(data)
        this.allNotes = allNotes
        notifyDataSetChanged()
    }

    //This method will filter the list
    //here we are passing the filtered data
    //and assigning it to the list with notifydatasetchanged method
    fun filterList(filterdNames: ArrayList<UserList>) {
        this.dataLeeds = filterdNames
        notifyDataSetChanged()
    }

    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val headingText = itemView.findViewById(R.id.user_id) as TextView
        val detailText = itemView.findViewById(R.id.detail) as TextView
        val imageView = itemView.findViewById(R.id.image_view) as ImageView
        val profileImageView = itemView.findViewById(R.id.imageView5) as ImageView


    }

    interface OnClickItemCallBack {
        fun onItemClick(position: Int, leeds: UserList)
    }
}