package com.example.brandat.ui.fragments.newcategory.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.brandat.R
import com.example.brandat.databinding.CategoryBottomSheetBinding
import com.example.brandat.ui.fragments.category.SharedViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip

class CategoryBottomSheet : BottomSheetDialogFragment() {

    private lateinit var binding: CategoryBottomSheetBinding

    private lateinit var model: SharedViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.category_bottom_sheet, container, false)

        binding = CategoryBottomSheetBinding.bind(view)
        model = ViewModelProvider(requireActivity())[SharedViewModel::class.java]

        val list = requireArguments().getStringArrayList("chipList")

        var chipSub: String
        var chipCategory: String

        if (list?.size == 2) {

            chipCategory = list[0]
            chipSub = list[1]

        } else {
            chipCategory = "MEN"
            chipSub = "SHOES"
        }


        when (chipCategory) {
            "Men" -> binding.chipGroupCategory.check(R.id.chipMen)
            "Women" -> binding.chipGroupCategory.check(R.id.chipWomen)
            "Kids" -> binding.chipGroupCategory.check(R.id.chipKids)
            "Sale" -> binding.chipGroupCategory.check(R.id.chipSale)
        }

        when (chipSub) {
            "Shoes" -> binding.chipGroupSub.check(R.id.chipShoes)
            "T-Shirts" -> binding.chipGroupSub.check(R.id.chipShirt)
            "Accessories" -> binding.chipGroupSub.check(R.id.chipAccess)
        }

        binding.chipGroupCategory.setOnCheckedChangeListener { group, selectedChipId ->
            chipCategory = group.findViewById<Chip>(selectedChipId).text.toString()
        }

        binding.chipGroupSub.setOnCheckedChangeListener { group, selectedChipId ->
            chipSub = group.findViewById<Chip>(selectedChipId).text.toString()
        }

        binding.btnApply.setOnClickListener {
            model.setCategory(chipCategory, chipSub)
            dismiss()
        }

        return binding.root
    }


}