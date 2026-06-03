package com.ramsons.metas.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.ramsons.metas.databinding.FragmentDashboardBinding
import com.ramsons.metas.util.FormatUtils

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DashboardViewModel by viewModels()
    private val adapter = MetaCardAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerMetas.adapter = adapter
        binding.recyclerMetas.layoutManager = GridLayoutManager(requireContext(), 2)

        viewModel.cards.observe(viewLifecycleOwner) { cards ->
            adapter.submitList(cards)
        }

        viewModel.resumo.observe(viewLifecycleOwner) { r ->
            binding.tvMes.text = r.mesNome
            binding.tvDiasRestantes.text = "${r.diasRestantes} dias úteis restantes"
            binding.tvTotalVendido.text = FormatUtils.formatBRLShort(r.totalVendido)
            binding.tvFaltaMetas.text = FormatUtils.formatBRLShort(r.totalFalta)
            binding.tvPorDia.text = FormatUtils.formatBRLShort(r.porDia)
            binding.tvMetasBatidas.text = "${r.metasBatidas}/${r.totalMetas}"
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.carregarDados()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
