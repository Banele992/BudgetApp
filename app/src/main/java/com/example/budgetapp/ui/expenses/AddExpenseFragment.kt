package com.example.budgetapp.ui.expenses

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.budgetapp.databinding.FragmentAddExpenseBinding
import com.google.firebase.storage.FirebaseStorage
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

class AddExpenseFragment : Fragment() {
    private var _binding: FragmentAddExpenseBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AddExpenseViewModel
    private var currentPhotoPath: String? = null
    private var photoFile: File? = null
    private val storage = FirebaseStorage.getInstance()
    private var receiptUrl: String? = null

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            dispatchTakePictureIntent()
        } else {
            Toast.makeText(context, "Camera permission is required to take photos", Toast.LENGTH_SHORT).show()
        }
    }

    private val takePictureLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            photoFile?.let { file ->
                uploadReceiptToFirebase(file)
            }
        }
    }

    private fun uploadReceiptToFirebase(file: File) {
        val storageRef = storage.reference
        val receiptRef = storageRef.child("receipts/${UUID.randomUUID()}.jpg")
        
        receiptRef.putFile(Uri.fromFile(file))
            .addOnSuccessListener {
                receiptRef.downloadUrl.addOnSuccessListener { uri ->
                    receiptUrl = uri.toString()
                    Toast.makeText(context, "Receipt uploaded successfully", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(context, "Failed to upload receipt: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddExpenseBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[AddExpenseViewModel::class.java]

        setupClickListeners()
        return binding.root
    }

    private fun setupClickListeners() {
        binding.addReceiptButton.setOnClickListener {
            checkCameraPermissionAndTakePicture()
        }

        binding.saveButton.setOnClickListener {
            // Get the expense details from the UI
            val amount = binding.amountEditText.text.toString().toDoubleOrNull()
            val description = binding.descriptionEditText.text.toString()
            val category = binding.categorySpinner.selectedItem.toString()
            val date = binding.dateEditText.text.toString()

            if (amount == null || description.isEmpty() || category.isEmpty() || date.isEmpty()) {
                Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Create expense object with receipt URL
            val expense = Expense(
                amount = amount,
                description = description,
                category = category,
                date = date,
                receiptUrl = receiptUrl
            )

            // Save expense to Firebase
            viewModel.addExpense(expense)
            Toast.makeText(context, "Expense saved successfully", Toast.LENGTH_SHORT).show()
            // Navigate back or clear form
        }
    }

    private fun checkCameraPermissionAndTakePicture() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                dispatchTakePictureIntent()
            }
            else -> {
                requestPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(requireActivity().packageManager)?.also {
                photoFile = try {
                    createImageFile()
                } catch (ex: Exception) {
                    Toast.makeText(context, "Error creating image file", Toast.LENGTH_SHORT).show()
                    null
                }

                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        requireContext(),
                        "${requireContext().packageName}.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    takePictureLauncher.launch(takePictureIntent)
                }
            }
        }
    }

    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir: File? = requireContext().getExternalFilesDir("Pictures")
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        ).apply {
            currentPhotoPath = absolutePath
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 