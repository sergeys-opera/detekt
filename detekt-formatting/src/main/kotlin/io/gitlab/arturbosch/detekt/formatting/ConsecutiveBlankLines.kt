package io.gitlab.arturbosch.detekt.formatting

import com.intellij.psi.PsiWhiteSpace
import com.intellij.psi.impl.source.tree.LeafPsiElement
import io.gitlab.arturbosch.detekt.api.CodeSmell
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Entity
import io.gitlab.arturbosch.detekt.api.TokenRule

/**
 * @author Artur Bosch
 */
class ConsecutiveBlankLines(config: Config) : TokenRule("ConsecutiveBlankLines", Severity.Style, config) {

	override fun visitSpaces(space: PsiWhiteSpace) {
		val parts = space.text.split("\n")
		if (parts.size > 3) {
			addFindings(CodeSmell(id, Entity.from(space, offset = 2)))
			withAutoCorrect {
				(space as LeafPsiElement).replaceWithText("${parts.first()}\n\n${parts.last()}")
			}
		}
	}

}