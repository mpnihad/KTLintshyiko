package com.nihad.custom_rules

import android.util.Log
import com.github.shyiko.ktlint.core.KtLint
import com.github.shyiko.ktlint.core.Rule
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.psi.KtImportDirective
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes

class NoInternalImportRule : Rule("no-wildcard-import") {

    override fun visit(
        node: ASTNode,
        autoCorrect: Boolean,
        emit: (
            offset: Int, errorMessage: String, canBeAutoCorrected:
            Boolean
        ) -> Unit
    ) {

        val ignoredFileNames = setOf<String>(
            "MainActivit.kt","MainActivit1.kt", "MainActivit2.kt", "MainActivit3.kt"
        )
        Log.d("TAG", "visit: ${ignoredFileNames.toString()}")

        val filePath = node.getUserData(KtLint.FILE_PATH_USER_DATA_KEY)?.substringAfterLast("/")
        if(ignoredFileNames.contains(filePath)){
            return
        }



        if (node.elementType == KtStubElementTypes.IMPORT_DIRECTIVE) {
            val importDirective = node.psi as KtImportDirective
            val path = importDirective.importPath?.pathStr
            if (path != null && path.contains("*")) {
                emit(
                    node.startOffset, "Importing wildcard",
                    false
                )
            }
        }
    }
}