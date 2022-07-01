package com.example.brandat.ui.fragments.newcategory.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.brandat.R
import com.example.brandat.databinding.CategoryBottomSheetTypeBinding
import com.example.brandat.ui.fragments.newcategory.SharedViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip

class CategoryBottomSheetType : BottomSheetDialogFragment() {

    private lateinit var binding: CategoryBottomSheetTypeBinding

    lateinit var model : SharedViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.category_bottom_sheet_type, container, false)

        binding = CategoryBottomSheetTypeBinding.bind(view)
        model = ViewModelProvider(requireActivity())[SharedViewModel::class.java]

        var type = requireArguments().getString("type")

        when (type) {
            "SHOES" -> binding.chipGroupSub.check(R.id.chipShoes)
            "T-SHIRTS" -> binding.chipGroupSub.check(R.id.chipShirt)
            "ACCESSORIES" -> binding.chipGroupSub.check(R.id.chipAccess)
            "ALL" -> binding.chipGroupSub.check(R.id.chipAll)
        }

        binding.chipGroupSub.setOnCheckedChangeListener{ group, selectedChipId ->
            type = group.findViewById<Chip>(selectedChipId).text.toString()
        }

        binding.btnApply.setOnClickListener{
            model.setProductType(type!!)
            dismiss()
        }

        return binding.root
    }


}