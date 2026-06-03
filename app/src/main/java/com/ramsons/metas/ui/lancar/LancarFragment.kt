package com.ramsons.metas.ui.lancar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ramsons.metas.R
import com.ramsons.metas.databinding.FragmentLancarBinding
import com.ramsons.metas.util.Categorias
import com.ramsons.metas.util.FormatUtils
import com.ramsons.metas.util.TipoCat

class LancarFragment : Fragment() {

    private var _binding: FragmentLancarBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LancarViewModel by viewModels()

    private val cats = Categorias.TODAS
    private var catSelecionada = cats[0]

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentLancarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup Spinner
        val nomes = cats.map { "${it.emoji} ${it.nome}" }
        val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, nomes)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerCategoria.adapter = spinnerAdapter

        binding.spinnerCategoria.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                catSelecionada = cats[position]
                atualizarLabel()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        // Data padrão: hoje
        binding.etData.setText(FormatUtils.hojeISO())

        binding.btnRegistrar.setOnClickListener {
            viewModel.registrarVenda(
                categoria = catSelecionada.chave,
                cliente = binding.etCliente.text.toString(),
                valorStr = binding.etValor.text.toString(),
                data = binding.etData.text.toString()
            )
        }

        viewModel.sucesso.observe(viewLifecycleOwner) { ok ->
            if (ok == true) {
                Toast.makeText(requireContext(), "${catSelecionada.emoji} Venda registrada!", Toast.LENGTH_SHORT).show()
                limparFormulario()
                viewModel.sucesso.value = false
            }
        }

        viewModel.erro.observe(viewLifecycleOwner) { msg ->
            if (!msg.isNullOrBlank()) {
                Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
                viewModel.erro.value = ""
            }
        }
    }

    private fun atualizarLabel() {
        if (catSelecionada.tipo == TipoCat.QUANTIDADE) {
            binding.tvLabelValor.text = "Quantidade de instalações"
            binding.etValor.hint = "1"
            binding.etValor.inputType = android.text.InputType.TYPE_CLASS_NUMBER
        } else {
            binding.tvLabelValor.text = "Valor (R$)"
            binding.etValor.hint = "0,00"
            binding.etValor.inputType = android.text.InputType.TYPE_CLASS_NUMBER or
                    android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL
        }
    }

    private fun limparFormulario() {
        binding.etValor.setText("")
        binding.etCliente.setText("")
        binding.etData.setText(FormatUtils.hojeISO())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
