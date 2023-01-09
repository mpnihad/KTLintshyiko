package com.nihad.custom_rules

import com.github.shyiko.ktlint.core.RuleSet
import com.github.shyiko.ktlint.core.RuleSetProvider


class CustomRuleSetProvider : RuleSetProvider {
    override fun get()
            = RuleSet("custom-rule", NoInternalImportRule())
}