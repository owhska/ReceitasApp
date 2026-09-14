package br.com.receitasapp.data

import br.com.receitasapp.model.Categoria
import br.com.receitasapp.model.Ingrediente
import br.com.receitasapp.model.Receita

/**
 * CAMADA MODEL / REPOSITORIO (Requisito 2 - MVVM).
 *
 * Fonte unica de dados do aplicativo. As ViewModels conversam com este objeto;
 * as Activities nunca acessam os dados diretamente.
 */
object ReceitaRepository {

    private val favoritas = mutableSetOf<Int>()

    private val receitas = listOf(
        Receita(
            id = 1,
            nome = "Brigadeiro de Colher",
            categoria = "Doces",
            emoji = "\uD83C\uDF6B",
            tempoPreparoMin = 20,
            porcoesBase = 4,
            dificuldade = "Facil",
            ingredientes = listOf(
                Ingrediente("Leite condensado", 1.0, "lata"),
                Ingrediente("Chocolate em po", 4.0, "colheres de sopa"),
                Ingrediente("Manteiga", 1.0, "colher de sopa"),
                Ingrediente("Granulado", 50.0, "g")
            ),
            modoPreparo = listOf(
                "Leve o leite condensado, o chocolate e a manteiga ao fogo baixo.",
                "Mexa sem parar por cerca de 10 minutos, ate desgrudar do fundo da panela.",
                "Transfira para um recipiente e deixe esfriar.",
                "Cubra com granulado e sirva de colher."
            )
        ),
        Receita(
            id = 2,
            nome = "Bolo de Cenoura",
            categoria = "Doces",
            emoji = "\uD83C\uDF82",
            tempoPreparoMin = 60,
            porcoesBase = 8,
            dificuldade = "Media",
            ingredientes = listOf(
                Ingrediente("Cenoura media", 3.0, "unidades"),
                Ingrediente("Ovos", 3.0, "unidades"),
                Ingrediente("Oleo", 200.0, "ml"),
                Ingrediente("Acucar", 300.0, "g"),
                Ingrediente("Farinha de trigo", 300.0, "g"),
                Ingrediente("Fermento em po", 1.0, "colher de sopa")
            ),
            modoPreparo = listOf(
                "Bata no liquidificador a cenoura, os ovos e o oleo.",
                "Em uma tigela, misture o acucar e a farinha e junte o liquido batido.",
                "Acrescente o fermento e mexa delicadamente.",
                "Asse em forno pre-aquecido a 180 graus por 40 minutos."
            )
        ),
        Receita(
            id = 3,
            nome = "Strogonoff de Frango",
            categoria = "Carnes",
            emoji = "\uD83C\uDF57",
            tempoPreparoMin = 40,
            porcoesBase = 4,
            dificuldade = "Facil",
            ingredientes = listOf(
                Ingrediente("Peito de frango em cubos", 600.0, "g"),
                Ingrediente("Creme de leite", 1.0, "lata"),
                Ingrediente("Molho de tomate", 200.0, "ml"),
                Ingrediente("Cebola picada", 1.0, "unidade"),
                Ingrediente("Champignon", 100.0, "g")
            ),
            modoPreparo = listOf(
                "Refogue a cebola e doure os cubos de frango.",
                "Adicione o molho de tomate e cozinhe por 10 minutos.",
                "Junte o champignon e o creme de leite e desligue antes de ferver.",
                "Sirva com arroz branco e batata palha."
            )
        ),
        Receita(
            id = 4,
            nome = "Macarrao Alho e Oleo",
            categoria = "Massas",
            emoji = "\uD83C\uDF5D",
            tempoPreparoMin = 25,
            porcoesBase = 2,
            dificuldade = "Facil",
            ingredientes = listOf(
                Ingrediente("Espaguete", 250.0, "g"),
                Ingrediente("Alho fatiado", 5.0, "dentes"),
                Ingrediente("Azeite de oliva", 60.0, "ml"),
                Ingrediente("Salsinha picada", 2.0, "colheres de sopa")
            ),
            modoPreparo = listOf(
                "Cozinhe o espaguete em agua com sal ate ficar al dente.",
                "Doure o alho no azeite em fogo baixo, sem queimar.",
                "Misture a massa escorrida ao alho e oleo.",
                "Finalize com salsinha e pimenta do reino."
            )
        ),
        Receita(
            id = 5,
            nome = "Lasanha a Bolonhesa",
            categoria = "Massas",
            emoji = "\uD83C\uDF5B",
            tempoPreparoMin = 75,
            porcoesBase = 6,
            dificuldade = "Dificil",
            ingredientes = listOf(
                Ingrediente("Massa de lasanha", 500.0, "g"),
                Ingrediente("Carne moida", 500.0, "g"),
                Ingrediente("Molho de tomate", 700.0, "ml"),
                Ingrediente("Presunto", 200.0, "g"),
                Ingrediente("Queijo mussarela", 300.0, "g")
            ),
            modoPreparo = listOf(
                "Prepare o molho refogando a carne moida com temperos e molho de tomate.",
                "Monte camadas alternando massa, molho, presunto e queijo.",
                "Cubra com papel aluminio e asse por 30 minutos.",
                "Retire o papel e gratine por mais 15 minutos."
            )
        ),
        Receita(
            id = 6,
            nome = "Salada Caesar",
            categoria = "Saladas",
            emoji = "\uD83E\uDD57",
            tempoPreparoMin = 15,
            porcoesBase = 2,
            dificuldade = "Facil",
            ingredientes = listOf(
                Ingrediente("Alface americana", 1.0, "pe"),
                Ingrediente("Croutons", 80.0, "g"),
                Ingrediente("Queijo parmesao ralado", 40.0, "g"),
                Ingrediente("Molho caesar", 80.0, "ml")
            ),
            modoPreparo = listOf(
                "Lave e rasgue as folhas de alface.",
                "Misture o molho caesar delicadamente nas folhas.",
                "Finalize com croutons e parmesao ralado."
            )
        ),
        Receita(
            id = 7,
            nome = "Sopa de Legumes",
            categoria = "Sopas",
            emoji = "\uD83C\uDF72",
            tempoPreparoMin = 45,
            porcoesBase = 4,
            dificuldade = "Facil",
            ingredientes = listOf(
                Ingrediente("Batata", 2.0, "unidades"),
                Ingrediente("Cenoura", 2.0, "unidades"),
                Ingrediente("Abobrinha", 1.0, "unidade"),
                Ingrediente("Caldo de legumes", 1.0, "litro")
            ),
            modoPreparo = listOf(
                "Descasque e corte todos os legumes em cubos.",
                "Refogue rapidamente e cubra com o caldo de legumes.",
                "Cozinhe por 25 minutos ate os legumes ficarem macios.",
                "Ajuste o sal e sirva quente."
            )
        ),
        Receita(
            id = 8,
            nome = "Vitamina de Banana",
            categoria = "Bebidas",
            emoji = "\uD83E\uDD5B",
            tempoPreparoMin = 5,
            porcoesBase = 2,
            dificuldade = "Facil",
            ingredientes = listOf(
                Ingrediente("Banana", 2.0, "unidades"),
                Ingrediente("Leite gelado", 400.0, "ml"),
                Ingrediente("Aveia em flocos", 2.0, "colheres de sopa"),
                Ingrediente("Mel", 1.0, "colher de sopa")
            ),
            modoPreparo = listOf(
                "Coloque todos os ingredientes no liquidificador.",
                "Bata por cerca de um minuto ate ficar cremoso.",
                "Sirva imediatamente."
            )
        ),
        Receita(
            id = 9,
            nome = "Pao de Queijo",
            categoria = "Lanches",
            emoji = "\uD83E\uDDC0",
            tempoPreparoMin = 50,
            porcoesBase = 6,
            dificuldade = "Media",
            ingredientes = listOf(
                Ingrediente("Polvilho azedo", 500.0, "g"),
                Ingrediente("Leite", 250.0, "ml"),
                Ingrediente("Oleo", 100.0, "ml"),
                Ingrediente("Ovos", 2.0, "unidades"),
                Ingrediente("Queijo meia cura ralado", 300.0, "g")
            ),
            modoPreparo = listOf(
                "Ferva o leite com o oleo e escalde o polvilho.",
                "Deixe amornar e acrescente os ovos e o queijo.",
                "Sove ate a massa ficar homogenea e enrole as bolinhas.",
                "Asse a 200 graus por cerca de 25 minutos."
            )
        ),
        Receita(
            id = 10,
            nome = "Hamburguer Caseiro",
            categoria = "Lanches",
            emoji = "\uD83C\uDF54",
            tempoPreparoMin = 30,
            porcoesBase = 4,
            dificuldade = "Media",
            ingredientes = listOf(
                Ingrediente("Carne moida", 600.0, "g"),
                Ingrediente("Pao de hamburguer", 4.0, "unidades"),
                Ingrediente("Queijo cheddar", 4.0, "fatias"),
                Ingrediente("Alface e tomate", 1.0, "porcao")
            ),
            modoPreparo = listOf(
                "Tempere a carne e modele os hamburgueres.",
                "Grelhe em frigideira bem quente por 3 minutos de cada lado.",
                "Derreta o queijo sobre a carne ainda na frigideira.",
                "Monte o lanche com os demais ingredientes."
            )
        ),
        Receita(
            id = 11,
            nome = "File de Tilapia Grelhado",
            categoria = "Carnes",
            emoji = "\uD83D\uDC1F",
            tempoPreparoMin = 20,
            porcoesBase = 2,
            dificuldade = "Facil",
            ingredientes = listOf(
                Ingrediente("File de tilapia", 400.0, "g"),
                Ingrediente("Limao", 1.0, "unidade"),
                Ingrediente("Azeite", 30.0, "ml"),
                Ingrediente("Alho amassado", 2.0, "dentes")
            ),
            modoPreparo = listOf(
                "Tempere os files com limao, alho e sal por 10 minutos.",
                "Grelhe em fogo medio por 4 minutos de cada lado.",
                "Regue com azeite e sirva com arroz e legumes."
            )
        ),
        Receita(
            id = 12,
            nome = "Mousse de Maracuja",
            categoria = "Doces",
            emoji = "\uD83C\uDF6E",
            tempoPreparoMin = 15,
            porcoesBase = 6,
            dificuldade = "Facil",
            ingredientes = listOf(
                Ingrediente("Leite condensado", 1.0, "lata"),
                Ingrediente("Creme de leite", 1.0, "lata"),
                Ingrediente("Suco concentrado de maracuja", 200.0, "ml")
            ),
            modoPreparo = listOf(
                "Bata todos os ingredientes no liquidificador por 3 minutos.",
                "Despeje em tacas ou refratario.",
                "Leve a geladeira por no minimo 3 horas."
            )
        )
    )

    fun listarTodas(): List<Receita> = receitas

    fun buscarPorId(id: Int): Receita? = receitas.firstOrNull { it.id == id }

    /**
     * FUNCAO KOTLIN COM PARAMETRO E RETORNO (Requisito 1).
     * Recebe o nome da categoria e devolve somente as receitas daquela categoria.
     */
    fun listarPorCategoria(categoria: String): List<Receita> =
        receitas.filter { it.categoria.equals(categoria, ignoreCase = true) }

    /**
     * FUNCAO KOTLIN COM PARAMETRO E RETORNO (Requisito 1).
     * Recebe um texto e devolve as receitas cujo nome, categoria ou ingredientes
     * contenham o termo pesquisado.
     */
    fun buscarPorTexto(texto: String): List<Receita> {
        val termo = texto.trim().lowercase()
        if (termo.isEmpty()) return receitas
        return receitas.filter { receita ->
            receita.nome.lowercase().contains(termo) ||
                receita.categoria.lowercase().contains(termo) ||
                receita.ingredientes.any { it.nome.lowercase().contains(termo) }
        }
    }

    /**
     * FUNCAO KOTLIN COM PARAMETRO E RETORNO (Requisito 1).
     * Monta a lista de categorias ja contando quantas receitas existem em cada uma.
     */
    fun listarCategorias(): List<Categoria> {
        val emojis = mapOf(
            "Doces" to "\uD83C\uDF6C",
            "Massas" to "\uD83C\uDF5D",
            "Carnes" to "\uD83E\uDD69",
            "Saladas" to "\uD83E\uDD57",
            "Sopas" to "\uD83C\uDF72",
            "Bebidas" to "\uD83E\uDD64",
            "Lanches" to "\uD83C\uDF54"
        )
        return receitas.groupBy { it.categoria }
            .map { (nome, lista) -> Categoria(nome, emojis[nome] ?: "\uD83C\uDF7D", lista.size) }
            .sortedBy { it.nome }
    }

    fun ehFavorita(id: Int): Boolean = favoritas.contains(id)

    /**
     * FUNCAO KOTLIN COM PARAMETRO E RETORNO (Requisito 1).
     * Marca ou desmarca a receita como favorita e retorna o novo estado.
     */
    fun alternarFavorita(id: Int): Boolean {
        return if (favoritas.contains(id)) {
            favoritas.remove(id)
            false
        } else {
            favoritas.add(id)
            true
        }
    }

    fun totalFavoritas(): Int = favoritas.size
}
