package com.ramsons.metas.util

data class Categoria(
    val chave: String,
    val nome: String,
    val emoji: String,
    val tipo: TipoCat,
    val unidade: String
)

enum class TipoCat { VALOR, QUANTIDADE }

object Categorias {
    val TODAS = listOf(
        Categoria("produtos",  "Produtos Vendidos",   "🛍️", TipoCat.VALOR,      "R$"),
        Categoria("garantia",  "Garantia Estendida",  "🛡️", TipoCat.VALOR,      "R$"),
        Categoria("seguro",    "Seguro",               "🔒", TipoCat.VALOR,      "R$"),
        Categoria("psd",       "PSD (Informática)",    "💻", TipoCat.VALOR,      "R$"),
        Categoria("crediario", "Crediário UME",        "💳", TipoCat.VALOR,      "R$"),
        Categoria("ar",        "Instalação Ar-cond.",  "❄️", TipoCat.QUANTIDADE, "un")
    )

    fun porChave(chave: String): Categoria? = TODAS.find { it.chave == chave }
}
