package com.ramsons.metas.util

import com.ramsons.metas.util.TipoCat
import java.text.NumberFormat
import java.util.Locale

object FormatUtils {
    private val brl = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
    private val brlNoDecimal = NumberFormat.getCurrencyInstance(Locale("pt", "BR")).apply {
        maximumFractionDigits = 0
        minimumFractionDigits = 0
    }

    fun formatValor(valor: Double, tipo: TipoCat): String {
        return if (tipo == TipoCat.QUANTIDADE) {
            "${valor.toInt()} un"
        } else {
            brl.format(valor)
        }
    }

    fun formatBRL(valor: Double): String = brl.format(valor)
    fun formatBRLShort(valor: Double): String = brlNoDecimal.format(valor)

    fun mesAtual(): String {
        val cal = java.util.Calendar.getInstance()
        val ano = cal.get(java.util.Calendar.YEAR)
        val mes = cal.get(java.util.Calendar.MONTH) + 1
        return "$ano-${mes.toString().padStart(2, '0')}"
    }

    fun hojeISO(): String {
        val cal = java.util.Calendar.getInstance()
        val ano = cal.get(java.util.Calendar.YEAR)
        val mes = (cal.get(java.util.Calendar.MONTH) + 1).toString().padStart(2, '0')
        val dia = cal.get(java.util.Calendar.DAY_OF_MONTH).toString().padStart(2, '0')
        return "$ano-$mes-$dia"
    }

    fun formatDataBR(dataISO: String): String {
        return try {
            val parts = dataISO.split("-")
            "${parts[2]}/${parts[1]}/${parts[0]}"
        } catch (e: Exception) {
            dataISO
        }
    }

    fun nomeMesAno(mesKey: String): String {
        return try {
            val parts = mesKey.split("-")
            val ano = parts[0]
            val mes = parts[1].toInt()
            val meses = arrayOf("","Janeiro","Fevereiro","Março","Abril","Maio","Junho",
                "Julho","Agosto","Setembro","Outubro","Novembro","Dezembro")
            "${meses[mes]} de $ano"
        } catch (e: Exception) {
            mesKey
        }
    }
}
