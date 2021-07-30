package com.raantech.solalat.user.ui.addhorse.fragments

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.raantech.solalat.user.R
import com.raantech.solalat.user.data.api.response.ResponseSubErrorsCodeEnum
import com.raantech.solalat.user.data.common.Constants
import com.raantech.solalat.user.data.common.CustomObserverResponse
import com.raantech.solalat.user.data.models.ServiceCategoriesResponse
import com.raantech.solalat.user.data.models.ServiceCategory
import com.raantech.solalat.user.data.models.media.Media
import com.raantech.solalat.user.databinding.FragmentAddHorseBinding
import com.raantech.solalat.user.ui.addhorse.adapter.CategoriesSpinnerAdapter
import com.raantech.solalat.user.ui.addhorse.adapter.GeneralStringDropDownAdapter
import com.raantech.solalat.user.ui.addhorse.adapter.SmallMediaRecyclerAdapter
import com.raantech.solalat.user.ui.addhorse.viewmodel.AddHorseViewModel
import com.raantech.solalat.user.ui.base.adapters.BaseBindingRecyclerViewAdapter
import com.raantech.solalat.user.ui.base.bindingadapters.setOnItemClickListener
import com.raantech.solalat.user.ui.base.fragment.BaseBindingFragment
import com.raantech.solalat.user.ui.media.MediaActivity
import com.raantech.solalat.user.utils.extensions.showErrorAlert
import com.raantech.solalat.user.utils.extensions.showValidationErrorAlert
import com.raantech.solalat.user.utils.extensions.validate
import com.raantech.solalat.user.utils.validation.ValidatorInputTypesEnums
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.layout_toolbar.*

@AndroidEntryPoint
class AddHorseFragment : BaseBindingFragment<FragmentAddHorseBinding>(),
    BaseBindingRecyclerViewAdapter.OnItemClickListener {

    private val viewModel: AddHorseViewModel by activityViewModels()

    lateinit var smallMediaRecyclerAdapter: SmallMediaRecyclerAdapter

    override fun getLayoutId(): Int = R.layout.fragment_add_horse
    private lateinit var categoriesSpinnerAdapter: CategoriesSpinnerAdapter
    private lateinit var genderSpinnerAdapter: GeneralStringDropDownAdapter
    private lateinit var ageSpinnerAdapter: GeneralStringDropDownAdapter
    private lateinit var safteySpinnerAdapter: GeneralStringDropDownAdapter
    private lateinit var heightSpinnerAdapter: GeneralStringDropDownAdapter
    override fun onViewVisible() {
        super.onViewVisible()
        addToolbar(
            hasToolbar = true,
            hasBackButton = true,
            showBackArrow = true,
            toolbarView = toolbar,
            hasTitle = true,
            title = R.string.add_horse,
            hasSubTitle = true,
            subTitle = R.string.add_new
        )
        setUpBinding()
        setUpListeners()
        init()
    }

    private fun init() {
        setUpRecyclerView()
        setUpCategories()
        setUpGender()
        setUpHorseAge()
        setUpHorseSaftey()
        setUpHorseHeight()
    }

    private fun setUpBinding() {
        binding?.viewModel = viewModel
    }

    private fun setUpListeners() {
        binding?.imgUpload?.setOnClickListener {
            MediaActivity.start(requireActivity(), true, resultLauncher)
        }

        binding?.btnAddProduct?.setOnClickListener {
            if (isDataValid()) {
                viewModel.category =
                    categoriesSpinnerAdapter.spinnerItems[categoriesSpinnerAdapter.index]
                viewModel.files.clear()
                viewModel.files.addAll(smallMediaRecyclerAdapter.items.map { it.id })
                viewModel.isPollinated = binding?.checkboxIsPollinated?.isChecked ?: false
                viewModel.gender = if (genderSpinnerAdapter.index == 0) "male" else "female"
                viewModel.safety = safteySpinnerAdapter.spinnerItems[safteySpinnerAdapter.index]
                viewModel.height = heightSpinnerAdapter.spinnerItems[heightSpinnerAdapter.index]
                viewModel.age = safteySpinnerAdapter.index + 1
                navigationController.navigate(R.id.action_addHorseFragment_to_addHorseStep2Fragment)
            }
        }
    }

    private fun setUpRecyclerView() {
        smallMediaRecyclerAdapter = SmallMediaRecyclerAdapter(requireContext())
        binding?.imagesRecyclerView?.adapter = smallMediaRecyclerAdapter
        binding?.imagesRecyclerView?.setOnItemClickListener(this)
    }

    private fun setUpCategories() {
        categoriesSpinnerAdapter =
            CategoriesSpinnerAdapter(binding!!.spinnerCategory, requireContext())
        categoriesSpinnerAdapter.let { binding?.spinnerCategory?.setSpinnerAdapter(it) }
        binding?.spinnerCategory?.getSpinnerRecyclerView()?.layoutManager =
            LinearLayoutManager(activity)
        binding?.spinnerCategory?.setOnSpinnerItemSelectedListener<ServiceCategory> { oldIndex, oldItem, newIndex, newItem ->
            binding?.spinnerCategory?.dismiss()
        }
        viewModel.getServicesCategories()
            .observe(this, categoriesResultObserver())
    }

    private fun setUpGender() {
        genderSpinnerAdapter =
            GeneralStringDropDownAdapter(binding!!.spinnerHorseGender, requireContext())
        genderSpinnerAdapter.let { binding?.spinnerHorseGender?.setSpinnerAdapter(it) }
        binding?.spinnerHorseGender?.getSpinnerRecyclerView()?.layoutManager =
            LinearLayoutManager(activity)
        binding?.spinnerHorseGender?.setOnSpinnerItemSelectedListener<String> { oldIndex, oldItem, newIndex, newItem ->
            binding?.spinnerHorseGender?.dismiss()
        }
        genderSpinnerAdapter.setItems(
            arrayListOf(
                resources.getString(R.string.male),
                resources.getString(R.string.female)
            )
        )
        binding?.spinnerHorseGender?.selectItemByIndex(0)
    }

    private fun setUpHorseAge() {
        ageSpinnerAdapter =
            GeneralStringDropDownAdapter(binding!!.spinnerAge, requireContext())
        ageSpinnerAdapter.let { binding?.spinnerAge?.setSpinnerAdapter(it) }
        binding?.spinnerAge?.getSpinnerRecyclerView()?.layoutManager =
            LinearLayoutManager(activity)
        binding?.spinnerAge?.setOnSpinnerItemSelectedListener<String> { oldIndex, oldItem, newIndex, newItem ->
            binding?.spinnerAge?.dismiss()
        }
        val list = mutableListOf<String>()
        for (i in 1 until 46) {
            list.add("$i ${resources.getString(if (i == 1) R.string.year_ else R.string.years_)}")
        }
        ageSpinnerAdapter.setItems(list)
        binding?.spinnerAge?.selectItemByIndex(0)
    }

    private fun setUpHorseSaftey() {
        safteySpinnerAdapter =
            GeneralStringDropDownAdapter(binding!!.spinnerSafety, requireContext())
        safteySpinnerAdapter.let { binding?.spinnerSafety?.setSpinnerAdapter(it) }
        binding?.spinnerSafety?.getSpinnerRecyclerView()?.layoutManager =
            LinearLayoutManager(activity)
        binding?.spinnerSafety?.setOnSpinnerItemSelectedListener<String> { oldIndex, oldItem, newIndex, newItem ->
            binding?.spinnerSafety?.dismiss()
        }
        safteySpinnerAdapter.setItems(
            arrayOf(
                resources.getString(R.string.weak),
                resources.getString(R.string.good),
                resources.getString(R.string.very_good),
                resources.getString(R.string.excellent)
            ).toList()
        )
        binding?.spinnerSafety?.selectItemByIndex(0)
    }

    private fun setUpHorseHeight() {
        heightSpinnerAdapter =
            GeneralStringDropDownAdapter(binding!!.spinnerHeight, requireContext())
        heightSpinnerAdapter.let { binding?.spinnerHeight?.setSpinnerAdapter(it) }
        binding?.spinnerHeight?.getSpinnerRecyclerView()?.layoutManager =
            LinearLayoutManager(activity)
        binding?.spinnerHeight?.setOnSpinnerItemSelectedListener<String> { oldIndex, oldItem, newIndex, newItem ->
            binding?.spinnerHeight?.dismiss()
        }
        val list = mutableListOf<String>()
        for (i in 1 until 46) {
            list.add("$i ${resources.getString(if (i < 10) R.string.meter else R.string.cm)}")
        }
        heightSpinnerAdapter.setItems(list)
        binding?.spinnerHeight?.selectItemByIndex(0)
    }

    private fun categoriesResultObserver(): CustomObserverResponse<ServiceCategoriesResponse> {
        return CustomObserverResponse(
            requireActivity(),
            object : CustomObserverResponse.APICallBack<ServiceCategoriesResponse> {
                override fun onSuccess(
                    statusCode: Int,
                    subErrorCode: ResponseSubErrorsCodeEnum,
                    data: ServiceCategoriesResponse?
                ) {
                    data?.categories?.let {
                        categoriesSpinnerAdapter.setItems(it)
                        binding?.spinnerCategory?.selectItemByIndex(0)
                    }
                }
            })
    }

    var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes
                val data: Intent? = result.data
                smallMediaRecyclerAdapter.submitItem(data?.getSerializableExtra(Constants.BundleData.MEDIA) as Media)
                binding?.imagesRecyclerView?.smoothScrollToPosition(smallMediaRecyclerAdapter.itemCount - 1)
            }
        }

    private fun isDataValid(): Boolean {
        if (categoriesSpinnerAdapter.index == -1) {
            requireActivity().showErrorAlert(
                resources.getString(R.string.app_name),
                resources.getString(R.string.please_select_horse_type)
            )
            return false
        }
        binding?.edHorseName?.text.toString().validate(
            ValidatorInputTypesEnums.TEXT,
            requireContext()
        )
            .let {
                if (!it.isValid) {
                    requireActivity().showValidationErrorAlert(
                        title = resources.getString(R.string.horse_name),
                        message = it.errorMessage
                    )
                    return false
                }
            }
        binding?.edFatherName?.text.toString().validate(
            ValidatorInputTypesEnums.TEXT,
            requireContext()
        )
            .let {
                if (!it.isValid) {
                    requireActivity().showValidationErrorAlert(
                        title = resources.getString(R.string.father_name),
                        message = it.errorMessage
                    )
                    return false
                }
            }
        binding?.edMotherName?.text.toString().validate(
            ValidatorInputTypesEnums.DOUBLE,
            requireContext()
        )
            .let {
                if (!it.isValid) {
                    requireActivity().showValidationErrorAlert(
                        title = resources.getString(R.string.mother_name),
                        message = it.errorMessage
                    )
                    return false
                }
            }

        if (smallMediaRecyclerAdapter.itemCount == 0) {
            requireActivity().showErrorAlert(
                resources.getString(R.string.horse_images),
                resources.getString(R.string.please_select_the_horse_images)
            )
            return false
        }

        return true
    }

    override fun onItemClick(view: View?, position: Int, item: Any) {
        if (item is Media) {
            if (view?.id == R.id.imgRemove) {
                smallMediaRecyclerAdapter.items.removeAt(position)
                smallMediaRecyclerAdapter.notifyItemRemoved(position)
            }
        }
    }


}