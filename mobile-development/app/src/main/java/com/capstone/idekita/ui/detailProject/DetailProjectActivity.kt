package com.capstone.idekita.ui.detailProject

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.capstone.idekita.MainViewModelFactory
import com.capstone.idekita.R
import com.capstone.idekita.adapter.ContributorProjectAdapter
import com.capstone.idekita.databinding.ActivityDetailProjectBinding
import com.capstone.idekita.response.ContributorsItem
import com.capstone.idekita.response.ProjectsItem
import com.capstone.idekita.response.RecommendationsItem
import com.capstone.idekita.result.TheResult
import com.capstone.idekita.ui.PmDetailSide.PmDetailFactory
import com.capstone.idekita.ui.PmDetailSide.PmDetailProjectActivity
import com.capstone.idekita.ui.PmDetailSide.PmDetailViewModel
import com.capstone.idekita.ui.PmDetailSide.contributorAcc.ContributorAccActivity
import com.capstone.idekita.ui.chatroom.ChatActivity
import com.capstone.idekita.ui.contributor.ContributorActivity
import com.capstone.idekita.ui.home.HomeViewModel
import java.net.IDN

//import com.capstone.idekita.model.wisataEntity

class DetailProjectActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailProjectBinding
    //private var project:ProjectsItem = null

    private val viewModel by viewModels<PmDetailViewModel> {
        PmDetailFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailProjectBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val project = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(EXTRA_DATA, RecommendationsItem::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_DATA)
        }

        //val project2 = intent.getParcelableExtra(EXTRA_DATA)

        binding.idProyek.text = project?.project?.id.toString()

        getDetail()

        binding.btSetFinish.setOnClickListener{
            val alertDialog = AlertDialog.Builder(this)
                .setTitle("Menyelesaikan Proyek")
                .setMessage("Anda tidak dapat mengubah lagi status proyek saat proyek itu selesai")
                .setPositiveButton("OK") { dialog, _ ->
                    viewModel.getToken().observe(this){
                        setThisFinish(it.token,project?.project?.id)
                    }
                    dialog.dismiss()
                }
                .setNegativeButton("Batal") { dialog, _ ->
                    // Aksi yang dilakukan saat tombol Batal ditekan
                    dialog.dismiss()
                }
                .create()

            alertDialog.show()
        }

        binding.btChat.setOnClickListener{
            viewModel.getToken().observe(this){
                val intent = Intent(this, ChatActivity::class.java)
                intent.putExtra(ChatActivity.PROJ_ID,project!!.project.id)
                intent.putExtra(ChatActivity.USER_NAME,it.name)
                startActivity(intent)
            }
        }

        binding.btCont.setOnClickListener{

            if (project != null){
                Toast.makeText(this,"button contributor", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, ContributorAccActivity::class.java)

                val IdProyek = project.project.id
                val bundle = Bundle()
                bundle.putInt("extra_id",IdProyek)
                intent.putExtras(bundle)
                //intent.putExtra(ProjectContributorFragment.EXTRA_DATA,IdProyek)

                startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(this as Activity).toBundle())
            }
        }

        binding.btnJoin.setOnClickListener {
            viewModel.getToken().observe(this){user->
                if (project != null){
                    joinKontributor(user.token,project.project.id)
                }

            }
        }

        binding.btSend.setOnClickListener {
            if(binding.rbRating.rating > 1){

                viewModel.getToken().observe(this){
                    if (project != null) {
                        setRating(it.token,project.project.id,binding.rbRating.rating.toInt())
                    }
                }
            }
        }

//         Set tombol berdasarkan siapa yang menekan project
        viewModel.getToken().observe(this){token ->
            if (project?.project?.creator == token.name){
                binding.btSetFinish.visibility = View.VISIBLE
                binding.btnJoin.visibility = View.GONE
            }else{
                binding.btSetFinish.visibility = View.GONE
                binding.btnJoin.visibility = View.VISIBLE
            }
            removeButtonWhenStatusSelesai(project?.project?.status)
            cekIsRating(token.token,token.name,project?.project?.id)
        }

    }
    private fun getDetail(){
        val project = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(EXTRA_DATA, RecommendationsItem::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_DATA)
        }

        val name = project?.project?.creator

        viewModel.getToken().observe(this){user ->
            if (user.name == name){
//                binding.btCont.visibility = View.VISIBLE
                binding.btSetFinish.visibility = View.VISIBLE
            }else{
//                binding.btCont.visibility = View.GONE
                binding.btSetFinish.visibility = View.GONE
            }
        }

        binding.apply {
            Glide.with(this@DetailProjectActivity)
                .load(project?.project?.gambar)
                .into(ivDetail)
            nameTV.text = project?.project?.nmProyek
            creatorTV.text = project?.project?.creator
            kategoriTV.text = project?.project?.category?.nmKategori
            descTV.text = project?.project?.deskripsi
            mulaiTV.text = project?.project?.tanggalMulai
            endTV.text = project?.project?.tanggalSelesai
            statusTV.text = project?.project?.status
            imgPm.setImageResource(R.drawable.holder_person)

        }
    }

    private fun getDetail(token : String, idProj : Int){
        viewModel.getProjById(token,idProj).observe(this){ data ->
            if(data != null){
                when(data){
                    is TheResult.Loading ->{

                    }
                    is TheResult.Success -> {
                        val res = data.data[0]

                        val project = if (Build.VERSION.SDK_INT >= 33) {
                            intent.getParcelableExtra(EXTRA_DATA, ProjectsItem::class.java)
                        } else {
                            @Suppress("DEPRECATION")
                            intent.getParcelableExtra(EXTRA_DATA)
                        }


                        binding.apply {
                            Glide.with(this@DetailProjectActivity)
                                .load(res.gambar)
                                .into(ivDetail)
                            nameTV.text = project?.nmProyek
                            creatorTV.text = project?.creator
                            kategoriTV.text = project?.category?.nmKategori
                            descTV.text = project?.deskripsi
                            mulaiTV.text = project?.tanggalMulai
                            endTV.text = project?.tanggalSelesai
                            statusTV.text = project?.status

                        }
                    }
                    is TheResult.Error -> {

                    }
                }
            }
        }
    }


    private fun joinKontributor(token: String,idProj: Int){

        viewModel.regisKon(token,idProj).observe(this){res->
            if (res != null) {
                when (res) {
                    is TheResult.Loading -> {

                    }
                    is TheResult.Success -> {
                        Toast.makeText(this, res.data.message, Toast.LENGTH_SHORT).show()
                    }
                    is TheResult.Error -> {
                        Toast.makeText(this, res.error, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    }

    private fun removeButtonWhenStatusSelesai(statusProj : String?){
        val isMine = intent.getBooleanExtra(IS_MINE,false)
        if(statusProj == "selesai" && isMine){
            binding.tvUlasan.visibility = View.VISIBLE
            binding.rbRating.visibility = View.VISIBLE
            binding.btSend.visibility = View.VISIBLE
            binding.btnJoin.visibility = View.GONE
            binding.btSetFinish.visibility = View.GONE
        }else if(statusProj == "selesai"){
            binding.btnJoin.visibility = View.GONE
        }
    }

    // rating

    private fun cekIsRating(token : String,userName : String, id_proyek : Int?){

        viewModel.getUserRating(token).observe(this){res ->
            if (res != null) {
                when (res) {
                    is TheResult.Loading -> {

                    }
                    is TheResult.Success -> {
                        val cekRate = res.data
                        for(i in cekRate){
                            Toast.makeText(this,"${id_proyek.toString()} asdasdas",Toast.LENGTH_SHORT).show()
                            if(i.username == userName && i.id_proyek == id_proyek){
                                binding.btSend.visibility = View.GONE
                                binding.rbRating.visibility = View.GONE
                                binding.tvUlasan.text = "Kamu sudah memberi rating"
                                Toast.makeText(this,"${i.username} + ${i.id_proyek}",Toast.LENGTH_SHORT).show()
                                Log.i("idProj","${i.id_proyek}")
                                break
                            }

                        }
                    }
                    is TheResult.Error -> {
//                        Toast.makeText(this, res.error, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
    fun setThisFinish(token : String,id : Int?){
        if (id != null) {
            viewModel.setThisProjFinish(token,id,"selesai").observe(this){result ->
                if (result != null) {
                    when (result) {
                        is TheResult.Loading -> {

                        }
                        is TheResult.Success -> {
                            finish()

                        }
                        is TheResult.Error -> {
                            //                        Toast.makeText(this, res.error, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    fun setRating(token : String, idProj : Int,nilai : Int){
        viewModel.setRating(token,idProj,nilai).observe(this){result ->
            if (result != null) {
                when (result) {
                    is TheResult.Loading -> {

                    }
                    is TheResult.Success -> {
                        finish()
//                        Toast.makeText(this, binding.rbRating.rating.toString(),Toast.LENGTH_SHORT).show()

                    }
                    is TheResult.Error -> {
                        //                        Toast.makeText(this, res.error, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    companion object{
        const val IS_MINE = "boolean"
        const val EXTRA_DATA = "EXTRA_DATA"
    }


}