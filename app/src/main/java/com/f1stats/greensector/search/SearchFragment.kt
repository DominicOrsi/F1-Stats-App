package com.f1stats.greensector.search

import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.f1stats.greensector.R

private const val TAG = "SearchFragment"

class SearchFragment : Fragment() {

    private lateinit var mainImageView: ImageView
    private lateinit var drawable: Drawable
    private lateinit var searchNameView: TextView
    private lateinit var oneNameView: TextView
    private lateinit var oneResultView: TextView
    private lateinit var twoNameView: TextView
    private lateinit var twoResultView: TextView
    private lateinit var threeNameView: TextView
    private lateinit var threeResultView: TextView
    private lateinit var fourNameView: TextView
    private lateinit var fourResultView: TextView
    private lateinit var fiveNameView: TextView
    private lateinit var fiveResultView: TextView
    private var searchId: Int = 1


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        arguments?.let { this.searchId = it.getInt("searchId")} // Get search Id

        // Initialize views for modification
        this.mainImageView = view.findViewById(R.id.search_result_image)
        this.searchNameView = view.findViewById(R.id.search_result_search_name)
        this.oneNameView = view.findViewById(R.id.search_result_name_one)
        this.oneResultView = view.findViewById(R.id.search_result_input_one)
        this.twoNameView = view.findViewById(R.id.search_result_name_two)
        this.twoResultView = view.findViewById(R.id.search_result_input_two)
        this.threeNameView  = view.findViewById(R.id.search_result_name_three)
        this.threeResultView = view.findViewById(R.id.search_result_input_three)
        this.fourNameView = view.findViewById(R.id.search_result_name_four)
        this.fourResultView = view.findViewById(R.id.search_result_input_four)
        this.fiveNameView = view.findViewById(R.id.search_result_name_five)
        this.fiveResultView = view.findViewById(R.id.search_result_input_five)

        this.getSearchInfo()
        return view
    }

    private fun getSearchInfo() {
        val searchInfo = SearchModel(this.searchId)

        if(searchId <= 4 || searchId >= 1300){
            try {
                var mainPicture = searchInfo.oneName.lowercase()

                // Getting String into a char array of the length of the string to then replace all
                // spaces and dashes with a underscore as to get the drawable
                val temp = CharArray(mainPicture.length)
                var counter = 0

                for(i in mainPicture.iterator()) {
                    if(i == '-' || i == ' ') {
                        temp[counter] = '_'
                    } else {
                        temp[counter] = i
                    }
                    counter++
                }
                mainPicture = String(temp)

                val uri = "@drawable/$mainPicture"
                val imageResource = getResources().getIdentifier(uri,
                    null, "com.f1stats.greensector")
                this.drawable = getResources().getDrawable(imageResource)
                this.mainImageView.setImageDrawable(this.drawable)
            } catch (e: Resources.NotFoundException) {
                Log.e(TAG, "Could not find ${searchInfo.oneName.lowercase()} drawable")
            }
        } else {
            try {
                // Getting Picture
                val fullName = searchInfo.oneName.lowercase()

                val driverPictureName = fullName.trim().split("\\s+".toRegex()).toTypedArray()

                val uri = "@drawable/${driverPictureName[1]}"
                val imageResource = getResources().getIdentifier(uri,
                    null, "com.f1stats.greensector")
                drawable = getResources().getDrawable(imageResource)    // I know this is depreciated but it works
                                                                        // can't find another way around this
                // Setting the drawable
                this.mainImageView.setImageDrawable(drawable)
            } catch (e: Resources.NotFoundException) {
                Log.e(TAG, "Could not find ${searchInfo.oneName.lowercase()} drawable")
            }
        }

        this.searchNameView.text = getString(R.string.search_name, searchInfo.searchName)
        this.oneNameView.text = getString(R.string.search_number_one_name, searchInfo.oneName)
        this.oneResultView.text = getString(R.string.search_number_one_result, searchInfo.oneResult)
        this.twoNameView.text = getString(R.string.search_number_two_name, searchInfo.twoName)
        this.twoResultView.text = getString(R.string.search_number_two_result, searchInfo.twoResult)
        this.threeNameView.text = getString(R.string.search_number_three_name, searchInfo.threeName)
        this.threeResultView.text = getString(R.string.search_number_three_result, searchInfo.threeResult)
        this.fourNameView.text = getString(R.string.search_number_four_name, searchInfo.fourName)
        this.fourResultView.text = getString(R.string.search_number_four_result, searchInfo.fourResult)
        this.fiveNameView.text = getString(R.string.search_number_five_name, searchInfo.fiveName)
        this.fiveResultView.text = getString(R.string.search_number_five_result, searchInfo.fiveResult)
    }
}

