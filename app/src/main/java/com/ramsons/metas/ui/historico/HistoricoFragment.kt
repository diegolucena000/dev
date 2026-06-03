package com.ramsons.metas.ui.historico

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ramsons.metas.databinding.FragmentHistoricoBinding

class HistoricoFragment : Fragment() {

    private var _binding: FragmentHistoricoBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HistoricoViewModel by viewModels()
    private lateinit var adapter: VendaAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHistoricoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = VendaAdapter { venda ->
            AlertDialog.Builder(requireContext())
                .setTitle("Remover venda")
                .setMessage("Deseja remover esta venda?")
                .setPositiveButton("Remover") { _, _ -> viewModel.deletarVenda(venda) }
                .setNegativeButton("Cancelar", null)
                .show()
        }

        binding.recyclerHistorico.adapter = adapter
        binding.recyclerHistorico.layoutManager = LinearLayoutManager(requireContext())

        viewModel.vendas.observe(viewLifecycleOwner) { lista ->
            adapter.submitList(lista)
            binding.tvVazio.visibility = if (lista.isEmpty()) View.VISIBLE else View.GONE
            binding.recyclerHistorico.visibility = if (lista.isEmpty()) View.GONE else View.VISIBLE
        }

        binding.btnLimparMes.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Limpar mês")
                .setMessage("Limpar TODAS as vendas deste mês? Esta ação não pode ser desfeita.")
                .setPositiveButton("Limpar") { _, _ -> viewModel.limparMes() }
                .setNegativeButton("Cancelar", null)
                .show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
