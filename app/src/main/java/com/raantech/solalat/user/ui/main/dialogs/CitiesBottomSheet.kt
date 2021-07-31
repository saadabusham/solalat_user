package com.raantech.solalat.user.ui.main.dialogs

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.raantech.solalat.user.R
import com.raantech.solalat.user.data.api.response.ResponseSubErrorsCodeEnum
import com.raantech.solalat.user.data.api.response.ResponseWrapper
import com.raantech.solalat.user.data.common.CustomObserverResponse
import com.raantech.solalat.user.data.models.City
import com.raantech.solalat.user.databinding.BottomSheetCitiesBinding
import com.raantech.solalat.user.ui.base.adapters.BaseBindingRecyclerViewAdapter
import com.raantech.solalat.user.ui.base.bindingadapters.setOnItemClickListener
import com.raantech.solalat.user.ui.main.adapters.CityRecyclerAdapter
import com.raantech.solalat.user.ui.main.viewmodels.MainViewModel
import com.raantech.solalat.user.utils.extensions.longToast
import com.raantech.solalat.user.utils.extensions.visible
import com.raantech.solalat.user.utils.recycleviewutils.VerticalSpaceDecoration

class CitiesBottomSheet(
    private val cityPickerCallBack: CityPickerCallBack,
    private val viewModel: MainViewModel,
    private val selectedCity: City?,
    private val singleSelection: Boolean = false
) :
    BottomSheetDialogFragment(), BaseBindingRecyclerViewAdapter.OnItemClickListener {

    lateinit var binding: BottomSheetCitiesBinding
    lateinit var cityRecyclerAdapter: CityRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);
    }

    override fun onStart() {
        super.onStart()
        view?.post {
            val parent = requireView().parent as View
            val params = (parent).layoutParams as CoordinatorLayout.LayoutParams
            val behavior = params.behavior
            val bottomSheetBehavior = behavior as BottomSheetBehavior
            bottomSheetBehavior.peekHeight = requireView().measuredHeight
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.bottom_sheet_cities, null, false)
        binding.viewModel = this
        isCancelable = true
        if (!singleSelection) {
            binding.btnSelect.visible()
        }
        setUpRecyclerView()
        return binding.root
    }

    private fun setUpRecyclerView() {
        cityRecyclerAdapter = CityRecyclerAdapter(requireContext(), singleSelection)
        binding.recyclerView.adapter = cityRecyclerAdapter
        binding.recyclerView.setOnItemClickListener(this)
        binding.recyclerView.addItemDecoration(
            VerticalSpaceDecoration(
                resources.getDimension(R.dimen._5sdp).toInt(),
                0
            )
        )
        viewModel.getCities().observe(this, citiesObserver())
    }

    private fun citiesObserver(): CustomObserverResponse<List<City>> {
        return CustomObserverResponse(
            requireActivity(),
            object : CustomObserverResponse.APICallBack<List<City>> {
                override fun onSuccess(
                    statusCode: Int,
                    subErrorCode: ResponseSubErrorsCodeEnum,
                    data: ResponseWrapper<List<City>>?
                ) {
                    data?.body?.let {
                        it.forEach { city ->
                            if (selectedCity?.name == city.name) {
                                city.selected = selectedCity?.selected == true
                            }
                        }
                        cityRecyclerAdapter.addItems(it)
                    }
                }
            }, showError = false
        )
    }


    fun onDoneClicked() {
        if (!cityRecyclerAdapter.items.filter { it.selected }.isNullOrEmpty()) {
            returnResults()
        } else {
            longToast(resources.getString(R.string.please_select_city))
        }
    }

    private fun returnResults() {
        dismiss()
        cityRecyclerAdapter.items.filter { it.selected }.also {
            cityPickerCallBack.callBack(it[0])
        }
    }

    fun onCancelClicked() {
        dismiss()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState) as BottomSheetDialog
    }


    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
    }

    interface CityPickerCallBack {
        fun callBack(citiesList: City) {}
    }

    override fun onItemClick(view: View?, position: Int, item: Any) {
        if (singleSelection) {
            returnResults()
        }
    }
}