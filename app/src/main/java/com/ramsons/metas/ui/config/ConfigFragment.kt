package com.ramsons.metas.ui.config

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ramsons.metas.databinding.FragmentConfigBinding
import com.ramsons.metas.util.Categorias

class ConfigFragment : Fragment() {

    private var _binding: FragmentConfigBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ConfigViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentConfigBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.state.observe(viewLifecycleOwner) { state ->
            binding.etMetaProdutos.setText(state.metas["produtos"]?.let { if (it == 0.0) "" else it.toInt().toString() } ?: "")
            binding.etMetaGarantia.setText(state.metas["garantia"]?.let { if (it == 0.0) "" else it.toInt().toString() } ?: "")
            binding.etMetaSeguro.setText(state.metas["seguro"]?.let { if (it == 0.0) "" else it.toInt().toString() } ?: "")
            binding.etMetaPsd.setText(state.metas["psd"]?.let { if (it == 0.0) "" else it.toInt().toString() } ?: "")
            binding.etMetaCrediario.setText(state.metas["crediario"]?.let { if (it == 0.0) "" else it.toInt().toString() } ?: "")
            binding.etMetaAr.setText(state.metas["ar"]?.let { if (it == 0.0) "" else it.toInt().toString() } ?: "")
            binding.etDiasUteis.setText(state.diasUteis.toString())
            binding.etDiasPassados.setText(state.diasPassados.toString())
        }

        binding.btnSalvarMetas.setOnClickListener {
            val valores = mapOf(
                "produtos" to binding.etMetaProdutos.text.toString(),
                "garantia" to binding.etMetaGarantia.text.toString(),
                "seguro" to binding.etMetaSeguro.text.toString(),
                "psd" to binding.etMetaPsd.text.toString(),
                "crediario" to binding.etMetaCrediario.text.toString(),
                "ar" to binding.etMetaAr.text.toString()
            )
            viewModel.salvarMetas(valores)
        }

        binding.btnSalvarDias.setOnClickListener {
            viewModel.salvarDias(
                binding.etDiasUteis.text.toString(),
                binding.etDiasPassados.text.toString()
            )
        }

        viewModel.salvoOk.observe(viewLifecycleOwner) { msg ->
            if (!msg.isNullOrBlank()) {
                Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
                viewModel.salvoOk.value = ""
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
