package com.example.brandat.ui.fragments.newcategory.bottomsheet

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.brandat.R
import com.example.brandat.databinding.CategoryBottomSheetBinding
import com.example.brandat.ui.fragments.category.IProduct
import com.example.brandat.ui.fragments.category.SharedViewModel
import com.example.brandat.ui.fragments.newcategory.NewCategoryFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip

class CategoryBottomSheet : BottomSheetDialogFragment() {

    private lateinit var binding: CategoryBottomSheetBinding

    lateinit var model : SharedViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.category_bottom_sheet, container, false)

        binding = CategoryBottomSheetBinding.bind(view)
        model = ViewModelProvider(requireActivity())[SharedViewModel::class.java]

        val list = requireArguments().getStringArrayList("chipList")

        var chipSub = ""
        var chipCategory = ""

        if(list != null) {
            chipCategory = list[0]
            chipSub = list[1]
        } else {
            chipCategory = "Men"
            chipSub = "Shoes"
        }

        when (chipCategory) {
            "Men" -> binding.chipGroupCategory.check(R.id.chipMen)
            "Women" -> binding.chipGroupCategory.check(R.id.chipWomen)
            "Kids" -> binding.chipGroupCategory.check(R.id.chipKids)
            "Sale" -> binding.chipGroupCategory.check(R.id.chipSale)
        }

        binding.chipGroupCategory.setOnCheckedChangeListener{ group, selectedChipId ->
            chipCategory = group.findViewById<Chip>(selectedChipId).text.toString()
            Log.d("TAG", "onCreateViewrrrrrr: $chipCategory")
        }

        binding.chipGroupSub.setOnCheckedChangeListener{ group, selectedChipId ->
            chipSub = group.findViewById<Chip>(selectedChipId).text.toString()
        }

        binding.btnApply.setOnClickListener{
            Log.d("TAG", "onCreateViewssssss: $chipCategory /// $chipSub")
            model.setCategory(chipCategory,chipSub)
            dismiss()
        }


        return binding.root
    }


}