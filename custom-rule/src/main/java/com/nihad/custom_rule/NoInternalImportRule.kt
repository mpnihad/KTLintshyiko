package com.nihad.custom_rule

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
            "MainActivity.kt","MainActivity1.kt", "MainActivity2.kt", "MainActivity3.kt"
        )
        println("TAG visit: ${ignoredFileNames.toString()}")

        val filePath = node.getUserData(KtLint.FILE_PATH_USER_DATA_KEY)?.substringAfterLast("/")
        if(ignoredFileNames.contains(filePath)){
            return
        }



        if (node.elementType == KtStubElementTypes.IMPORT_DIRECTIVE) {
            val importDirective = node.psi as KtImportDirective
            val path = importDirective.importPath?.pathStr
            if (path != null && path.endsWith("*")) {
                emit(
                    node.startOffset, "Importing wildcard",
                    false
                )
            }
        }
    }
}
//
//class InsertSuppressValueRule : Rule("add-file-to-Ignore") {
//
//
//    override fun visit(
//        node: ASTNode,
//        autoCorrect: Boolean,
//        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit
//    ) {
//
//        val ignoredFileNames = setOf<String>(
//            "MainActivity.kt","MainActivity1.kt", "MainActivity2.kt", "MainActivity3.kt"
//        )
//
//        val filePath = node.getUserData(KtLint.FILE_PATH_USER_DATA_KEY)?.substringAfterLast("/")
//        if(!(ignoredFileNames.contains(filePath))){
//            return
//        }
//
//        if (node.elementType == KtStubElementTypes.FILE) {
//            val ec = EditorConfig.from(node as FileASTNode)
//            val insertFinalNewline = ec.insertFinalNewline ?: return
//            val firstNode = firstChildNodeOf(node)
//            if (insertFinalNewline) {
//                if (!firstNode.textContains('@file:Suppress("ktlint")')) {
//                    // (PsiTreeUtil.getDeepestLast(lastNode.psi).node ?: lastNode).startOffset
//                    emit(0, "File must end with a newline (\\n)", true)
//                    if (autoCorrect) {
//                        node.addChild(PsiWhiteSpaceImpl("\n"), null)
//                    }
//                }
//            } else {
//                if (lastNode is PsiWhiteSpace && lastNode.textContains('\n')) {
//                    emit(lastNode.startOffset, "Redundant newline (\\n) at the end of file", true)
//                    if (autoCorrect) {
//                        lastNode.node.treeParent.removeChild(lastNode.node)
//                    }
//                }
//            }
//        }
//    }
//    private tailrec fun firstChildNodeOf(node: ASTNode): ASTNode? =
//        if (node.firstChildNode == null) node else lastChildNodeOf(node.firstChildNode)
//}
