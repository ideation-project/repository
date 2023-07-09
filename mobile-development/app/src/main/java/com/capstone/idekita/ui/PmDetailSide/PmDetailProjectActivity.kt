package com.capstone.idekita.ui.PmDetailSide

import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityOptionsCompat
import com.bumptech.glide.Glide
import com.capstone.idekita.R
import com.capstone.idekita.api.ApiConfig
import com.capstone.idekita.databinding.ActivityAddProjectBinding
import com.capstone.idekita.databinding.ActivityPmDetailProjectBinding
import com.capstone.idekita.response.ProjectsItem
import com.capstone.idekita.response.RecommendationsItem
import com.capstone.idekita.response.RegisContributorResponse
import com.capstone.idekita.response.RegisterResponse
import com.capstone.idekita.result.TheResult
import com.capstone.idekita.ui.PmDetailSide.contributorAcc.ContributorAccActivity
import com.capstone.idekita.ui.PmDetailSide.contributorAcc.ProjectContributorFragment
import com.capstone.idekita.ui.addProject.AddProjectFactory
import com.capstone.idekita.ui.addProject.AddProjectViewModel
import com.capstone.idekita.ui.chatroom.ChatActivity
import com.capstone.idekita.ui.detailProject.DetailProjectActivity
import com.capstone.idekita.ui.home.HomeFragment
import com.capstone.idekita.ui.login.LoginActivity
import com.capstone.idekita.ui.register.RegisterActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PmDetailProjectActivity : AppCompatActivity() {

    private lateinit var binding : ActivityPmDetailProjectBinding
    private val viewModel by viewModels<PmDetailViewModel> {
        PmDetailFactory.getInstance(this)
    }


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPmDetailProjectBinding.inflate(layoutInflater)
        setContentView(binding.root)



        val project = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(EXTRA_DATA, ProjectsItem::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_DATA)
        }

        val isFromRekom = intent.getBooleanExtra(REKOM,false)

        if(isFromRekom){
            getDetailFromRekomen()
        }else{
            getDetail()
        }





        binding.btSetFinish.setOnClickListener{
            val alertDialog = AlertDialog.Builder(this)
                .setTitle("Menyelesaikan Proyek")
                .setMessage("Anda tidak dapat mengubah lagi status proyek saat proyek itu selesai")
                .setPositiveButton("OK") { dialog, _ ->
                    viewModel.getToken().observe(this){
                        setThisFinish(it.token,project?.id)
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
               val intent = Intent(this,ChatActivity::class.java)
               intent.putExtra(ChatActivity.PROJ_ID,project!!.id)
            intent.putExtra(ChatActivity.USER_NAME,it.name)
               startActivity(intent)
           }
        }

        binding.btCont.setOnClickListener{

            if (project != null){
                Toast.makeText(this,"button contributor",Toast.LENGTH_SHORT).show()
                val intent = Intent(this,ContributorAccActivity::class.java)

                val IdProyek = project.id
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
                    joinKontributor(user.token,project.id)
                }

            }
        }

        binding.btSend.setOnClickListener {
            if(binding.rbRating.rating > 1){

                viewModel.getToken().observe(this){
                    if (project != null) {
                        setRating(it.token,project.id,binding.rbRating.rating.toInt())
                    }
                }
            }
        }

//         Set tombol berdasarkan siapa yang menekan project
        viewModel.getToken().observe(this){token ->
            if (project?.creator == token.name){
                binding.btSetFinish.visibility = View.VISIBLE
                binding.btnJoin.visibility = View.GONE
            }else{
                binding.btSetFinish.visibility = View.GONE
                binding.btnJoin.visibility = View.VISIBLE
            }
            removeButtonWhenStatusSelesai(project?.status)
            setButtonWaiting(token.token,project!!.id,token.name)
            getKon(token.token,project.id,token.name)
            cekIsRating(token.token,token.name,project?.id)
        }



    }


    @RequiresApi(Build.VERSION_CODES.M)
    private fun getDetail(){
        val project = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(EXTRA_DATA, ProjectsItem::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_DATA)
        }

        val name = project?.creator

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
            Glide.with(this@PmDetailProjectActivity)
                .load(project?.gambar)
                .into(ivDetail)
            nameTV.text = project?.nmProyek
            creatorTV.text = project?.creator
            kategoriTV.text = project?.category?.nmKategori
            descTV.text = project?.deskripsi
            mulaiTV.text = project?.tanggalMulai
            endTV.text = project?.tanggalSelesai
            statusTV.text = project?.status
            imgPm.setImageResource(R.drawable.holder_person)

            if(project?.status == "selesai"){
                binding.cardStatus.setCardBackgroundColor(getColor(R.color.green))
            }else if(project?.status == "terbuka"){
                binding.cardStatus.setCardBackgroundColor(getColor(R.color.default_grey))
                binding.statusTV.setTextColor(getColor(R.color.black))
            }
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
                            Glide.with(this@PmDetailProjectActivity)
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

    private fun getDetailFromRekomen(){
        val rekomen = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(EXTRA_DATA, RecommendationsItem::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_DATA)
        }

        val name = rekomen?.project?.creator

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
            Glide.with(this@PmDetailProjectActivity)
                .load(rekomen?.project?.gambar)
                .into(ivDetail)
            nameTV.text = rekomen?.project?.nmProyek
            creatorTV.text = rekomen?.project?.creator
            kategoriTV.text = rekomen?.project?.category?.nmKategori
            descTV.text = rekomen?.project?.deskripsi
            mulaiTV.text = rekomen?.project?.tanggalMulai
            endTV.text = rekomen?.project?.tanggalSelesai
            statusTV.text = rekomen?.project?.status
            imgPm.setImageResource(R.drawable.holder_person)

        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun joinKontributor(token: String, idProj: Int){

                viewModel.regisKon(token,idProj).observe(this){res->
                    if (res != null) {
                        when (res) {
                            is TheResult.Loading -> {

                            }
                            is TheResult.Success -> {
                                Toast.makeText(this, res.data.message, Toast.LENGTH_SHORT).show()
                                binding.btnJoin.setBackgroundColor(getColor(R.color.default_grey))
                                binding.btnJoin.isClickable = false
                                binding.btnJoin.text = "Sedang Menunggu"
                            }
                            is TheResult.Error -> {
                                Toast.makeText(this, res.error, Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setButtonWaiting(token : String, idProj : Int, name : String){
        viewModel.getWaiting(token,idProj).observe(this){res ->
            if (res != null) {
                when (res) {
                    is TheResult.Loading -> {

                    }
                    is TheResult.Success -> {
                        val dList = res.data
                        for (i in dList){
                            if(i.username == name && i.statusLamaran == "menunggu"){
                                binding.btnJoin.setBackgroundColor(getColor(R.color.default_grey))
                                binding.btnJoin.isClickable = false
                                binding.btnJoin.text = "Sedang Menunggu"
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

    @RequiresApi(Build.VERSION_CODES.M)
    fun getKon(token : String, idProj : Int, userName : String){
        viewModel.getContributor(token,idProj).observe(this){res ->
            if (res != null) {
                when (res) {
                    is TheResult.Loading -> {

                    }
                    is TheResult.Success -> {
                        val cekRate = res.data
                        for(i in cekRate){
                            if(i.username == userName){
                                binding.btnJoin.setBackgroundColor(getColor(R.color.default_grey))
                                binding.btnJoin.isClickable = false
                                binding.btnJoin.text = "Anda adalah kontributor"
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
                            if(i.username == userName && i.id_proyek == id_proyek){
                                binding.btSend.visibility = View.GONE
                                binding.rbRating.visibility = View.GONE
                                binding.rbRatingRead.visibility = View.VISIBLE
                                binding.rbRatingRead.rating = i.nilai.toFloat()
                                binding.tvUlasan.text = "Rating proyek"
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
        const val REKOM = "dari_rekomendasi"
        const val IS_MINE = "boolean"
        const val EXTRA_DATA = "EXTRA_DATA"
    }
}