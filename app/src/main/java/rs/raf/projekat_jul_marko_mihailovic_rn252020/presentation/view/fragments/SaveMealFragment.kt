package rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.fragments

import android.Manifest
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.projekat_jul_marko_mihailovic_rn252020.R
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.models.Meal
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.models.SavedMeal
import rs.raf.projekat_jul_marko_mihailovic_rn252020.databinding.FragmentSaveMealBinding
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.contract.SaveMealContract
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.states.AddMealState
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.viewmodel.SavedMealViewModel
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.time.LocalDate
import java.time.ZoneId
import java.util.Calendar


class SaveMealFragment(
    private val meal: Meal
): Fragment(R.layout.fragment_save_meal) {

    private lateinit var datePickerDialog: DatePickerDialog
    private val mainViewModel: SaveMealContract.ViewModel by sharedViewModel<SavedMealViewModel>()
    private var _binding: FragmentSaveMealBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val calendar = Calendar.getInstance()
    private var year: Int = calendar.get(Calendar.YEAR)
    private var month: Int = calendar.get(Calendar.MONTH)
    private var day: Int = calendar.get(Calendar.DAY_OF_MONTH)

    private var currentImagePath: String? = null
    companion object {
        const val DORUCAK = "DORUCAK"
        const val RUCAK = "RUCAK"
        const val UZINA = "UZINA"
        const val VECERA = "VECERA"
        const val CAMERA_PERMISSION_REQUEST_CODE = 100
        const val CAMERA_REQUEST_CODE = 101
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSaveMealBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        initUi()
        initObservers()
    }

    private fun initUi() {
        initListeners()
        initData()
        initDatePicker()
    }

    private fun openCameraDialog() {
        val cameraPermission = Manifest.permission.CAMERA
        val permissionCheck = ContextCompat.checkSelfPermission(
            requireContext(),
            cameraPermission
        )

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                arrayOf(cameraPermission),
                CAMERA_PERMISSION_REQUEST_CODE
            )
        } else {
            openCamera()
        }
    }

    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera()
            } else {
                showPermissionDeniedDialog()
            }
        }
    }

    private fun showPermissionDeniedDialog() {
        val dialog = AlertDialog.Builder(requireContext())
        dialog.setTitle("Permission Denied")
        dialog.setMessage("Please grant camera permission to take a photo.")
        dialog.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            val photo: Bitmap = data?.extras?.get("data") as Bitmap
            binding.imageView3.setImageBitmap(photo)

            // Save the image
            currentImagePath = saveImage(photo)
            Timber.e("Putanja slike je " + currentImagePath)
        }
    }

    private fun saveImage(bitmap: Bitmap): String? {
        val imagesDir = requireContext().getExternalFilesDir(null) // Get the directory for storing images

        val file = File(imagesDir, "my_image" + LocalDate.now().toString() +".jpg")
        val outputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        outputStream.flush()
        outputStream.close()

        return file.absolutePath
    }

    private fun initDatePicker() {
        datePickerDialog = DatePickerDialog(requireContext(),{view,year,month,dayOfMonth ->
            this.year = year
            this.month = month
            this.day = dayOfMonth
            Timber.e(String.format("Cuvam %d, a prikazujem %d", this.month, month))
            binding.buttonDatePicker.text = String.format("%d.%d.%d", dayOfMonth, month + 1, year)
        },year,month,day)
    }

    private fun initData() {
        Picasso.get().load(meal.strMealThumb).into(binding.imageView3)
        binding.mealName.text = meal.strMeal
        binding.buttonDatePicker.text = String.format("%d.%d.%d", day, month + 1, year)
        binding.dorucak.isSelected = true
    }

    private fun initListeners() {
//        binding.buttonSaveMeal.setOnClickListener {
//            parentFragmentManager.beginTransaction()
//                .replace(R.id.fragmentContainerView, SaveMealFragment(mealDetails))
//                .addToBackStack(null)
//                .commit()
//        }
        binding.buttonDatePicker.setOnClickListener {
            datePickerDialog.show()
        }
        binding.imageView3.setOnClickListener {
            openCameraDialog()
        }
        binding.buttonSaveMeal.setOnClickListener {
            val obrok: String
            if(binding.dorucak.isChecked)
                obrok = DORUCAK
            else if(binding.rucak.isChecked)
                obrok = RUCAK
            else if(binding.uzina.isChecked)
                obrok = UZINA
            else obrok = VECERA
            val slika: String
            if(currentImagePath != null)
                slika = currentImagePath as String
            else slika = meal.strMealThumb

            Timber.e("Konacna slika je " + slika)

            val savedMeal = SavedMeal(
                meal.idMeal,
                meal.strMeal,
                meal.strCategory,
                meal.strInstructions,
                slika,
                meal.strYoutube,
                obrok,
                LocalDate.of(year,month + 1,day).atStartOfDay(ZoneId.systemDefault()).toEpochSecond(),
                meal.strIngredient1,
                meal.strIngredient2,
                meal.strIngredient3,
                meal.strIngredient4,
                meal.strIngredient5,
                meal.strIngredient6,
                meal.strIngredient7,
                meal.strIngredient8,
                meal.strIngredient9,
                meal.strIngredient10,
                meal.strIngredient11,
                meal.strIngredient12,
                meal.strIngredient13,
                meal.strIngredient14,
                meal.strIngredient15,
                meal.strIngredient16,
                meal.strIngredient17,
                meal.strIngredient18,
                meal.strIngredient19,
                meal.strIngredient20,
                meal.strMeasure1,
                meal.strMeasure2,
                meal.strMeasure3,
                meal.strMeasure4,
                meal.strMeasure5,
                meal.strMeasure6,
                meal.strMeasure7,
                meal.strMeasure8,
                meal.strMeasure9,
                meal.strMeasure10,
                meal.strMeasure11,
                meal.strMeasure12,
                meal.strMeasure13,
                meal.strMeasure14,
                meal.strMeasure15,
                meal.strMeasure16,
                meal.strMeasure17,
                meal.strMeasure18,
                meal.strMeasure19,
                meal.strMeasure20
            )
            mainViewModel.insert(savedMeal)
        }
    }

    private fun initObservers() {
        mainViewModel.saveMealState.observe(viewLifecycleOwner, Observer {
            Timber.e(it.toString())
            renderState(it)
        })
    }

    private fun renderState(state: AddMealState) {
        when (state) {
            is AddMealState.Success -> {
                showLoadingState(false)
                Toast.makeText(context, "Jelo uspesno sacuvano u meni!", Toast.LENGTH_LONG).show()
            }
            is AddMealState.Error -> {
                showLoadingState(false)
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showLoadingState(loading: Boolean) {
//        binding.inputEt.isVisible = !loading
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}