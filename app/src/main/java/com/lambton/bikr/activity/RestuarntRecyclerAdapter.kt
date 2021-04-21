package com.example.mohitmvvmfirebase.ui.activity.emp_posts

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.lambton.bikr.R
import com.lambton.bikr.activity.DelievryDetailActivity
import com.lambton.bikr.activity.RestaurentsActivity
import com.lambton.bikr.firebase.Restaurant_model
import com.mikhaellopez.circularimageview.CircularImageView


/**
 * Created by ThinkSoft on 20/12/2017.
 */
class RestuarntRecyclerAdapter(contacts: ArrayList<Restaurant_model>, contx: Context, listener: RestaurentsActivity, currentLat: Double, currentLong: Double) : RecyclerView.Adapter<RestuarntRecyclerAdapter.RecyclerViewHolder>() {
    var  currentLatii:Double = currentLat
    var  currentLongi:Double = currentLong

    override fun onBindViewHolder(holder: RecyclerViewHolder, p1: Int) {

//        if(!isEmpty) {
            var user: Restaurant_model = filteredlistContacts[p1]

//        holder!!.ll_main.setOnClickListener(View.OnClickListener {
//            var i = Intent(ctx, PostDetailActivity::class.java)
//
//            var post = Add_post(user.uid,user.post_id,user.jobTitle,user.jobDescription,
//                user.jobLoc,user.jobDate,user.jobType,user.jobPackage,user.isFavourite,user.fav,null)
//
//            i.putExtra("post_data",post)
//            i.putExtra("post_position",p1)
//            //         it.putExtra("userid",sender_id)
////                    ///   it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//            listenerContact.startActivity(i)
//        })

        holder!!.textViewName.text = user.name
        holder!!. textViewEmail.text = user.address
        holder!!.tvDistance.text = user.distance.toString() + " Km"
        holder!!.tv_loc.text = user.description

        Glide.with(ctx)
                .load(user.image)
                .apply(RequestOptions.circleCropTransform().placeholder(R.drawable.ic_uservbv).error(
                        R.drawable.ic_uservbv))
                .into(holder!!.user_profile_photo)//binding?.userProfilePhoto



//        if(user.appliedPosts!!.size>0){
//
//            holder!!. tv_apply.text = "Check Responses"
//        }else{
//
//
//            holder!!.tv_apply.text = "No Response yet"
//            //.setImageResource(R.drawable.unfav)
//        }
//        if(user.favPost!!.isFavour){
//
//            holder!!.iv_fav.setImageResource(R.drawable.fav)
//        }else{
//            holder!!. iv_fav.setImageResource(R.drawable.unfav)
//        }
//
         //   holder.bind(user, listenerContact)
//        }else{
//            holder!!.mName.text = "NO Results FOUND!"
//           // holder!!.mCountry.text = country.toString()
//           // holder!!.mGender.text =
//        }



//        holder!!.iv_fav.setOnClickListener(View.OnClickListener {
//
//            Log.e("ISFA1",user.isFavoue.toString())
//            Log.e("ISFA2 ",user.isFavourite.toString())
//            if(user.favPost!!.isFavour){
//                //user.isFavourite = false
//                //userList[position]
//                holder!!. iv_fav.setImageResource(R.drawable.unfav)
//                listenerContact.onFavChange(false,p1)
//            }else{
//                holder!!.iv_fav.setImageResource(R.drawable.fav)
//                listenerContact.onFavChange(true,p1)
//            }
//        })
        holder!!.tv_apply.setOnClickListener(View.OnClickListener {

//            if(user.appliedPosts!!.size>0){
                var i = Intent(ctx, DelievryDetailActivity::class.java)
               i.putExtra("rest_data",user)
            i.putExtra("distance",user.distance)
            i.putExtra("current_lat",currentLatii)
            i.putExtra("current_long",currentLongi)
//                i.putParcelableArrayListExtra("responders_list",user.appliedPosts)
//                //         it.putExtra("userid",sender_id)
////                    ///   it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                listenerContact.startActivity(i)
//            else{
             //   Toast.makeText(ctx,"You Have no response for your this job post", Toast.LENGTH_SHORT).show()
//
//            }
        })

    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerViewHolder {

    return RecyclerViewHolder(LayoutInflater.from(p0!!.context).inflate(
        R.layout.list_layout_rest, p0,
            false))
}



    // var isEmpty = false

    private var listContacts: ArrayList<Restaurant_model> = contacts
    private var filteredlistContacts: ArrayList<Restaurant_model> = contacts

    private var ctx: Context = contx
   private var listenerContact: RestaurentsActivity = listener

//    interface OnItemClickListener {
//        fun onItemClick(contact:Add_post)
//    }

    interface AdapterList_Listner {
        fun onFavChange(isFav: Boolean, position: Int)
        // var arrayList = ArrayList<Add_post>()
    }

    override fun getItemCount(): Int {
      //  var i: Int = filteredlistContacts.size
//        if(isEmpty){
//            return 1
//        }else {
            return filteredlistContacts.size
      //  }
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }




//    fun getFilter(): Filter {
//        return object : Filter() {
//            protected override fun publishResults(constraint: CharSequence, results: FilterResults) {
//                filteredlistContacts = results.values as ArrayList<Restaurant_model>
//
//                Log.e("SIZEEEE",filteredlistContacts.size.toString())
//                notifyDataSetChanged()
//            }
//
//            protected override fun performFiltering(constraint: CharSequence): FilterResults {
//                var filteredResults: ArrayList<Restaurant_model>? = null
//                if (constraint.length == 0) {
//                    filteredResults = listContacts
//                } else {
//                    filteredResults = getFilteredResults(constraint.toString().toLowerCase())
//                }
//
//                val results = FilterResults()
//                results.values = filteredResults
//
//                return results
//            }
//        }
//    }

//    protected fun getFilteredResults(constraint: String): ArrayList<Restaurant_model> {
//        val results: ArrayList<Restaurant_model> = ArrayList()
//
//        for (item in listContacts) {
//            if (item.jobTitle!!.toLowerCase().contains(constraint) ||
//                item.jobLoc!!.toLowerCase().contains(constraint)    ) {
//                results.add(item)
//            }
//        }
//        return results
//    }


    fun addContacts(listContacts: ArrayList<Restaurant_model>) {
        this.listContacts = listContacts
        this.filteredlistContacts = listContacts
        notifyDataSetChanged()
    }



    class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


//        var ll_main =  itemView.findViewById(R.id.ll_main) as LinearLayout
        var textViewName = itemView.findViewById(R.id.tv_list_title) as TextView
        var textViewEmail = itemView.findViewById(R.id.tv_list_email) as TextView
        var tvDistance = itemView.findViewById(R.id.tvDistance) as TextView
        var tv_loc = itemView.findViewById(R.id.tv_loc) as TextView
        var tv_apply = itemView.findViewById(R.id.tv_apply) as TextView


       var  user_profile_photo = itemView.findViewById(R.id.user_profile) as CircularImageView
        //  val iv_profile = itemView.findViewById(R.id.iv_list_profile) as ImageView
//        var iv_fav = itemView.findViewById(R.id.iv_fav) as ImageView

//        fun bind(contact: Add_post, listener: OnItemClickListener) {
//            itemView.setOnClickListener {
//                listener.onItemClick(contact)
//            }
//        }
//interface AdapterList_Listner {
//    fun onFavChange(isFav: Boolean, position: Int)
//    // var arrayList = ArrayList<Add_post>()
//}
    }


}