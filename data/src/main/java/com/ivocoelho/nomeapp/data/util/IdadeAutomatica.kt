package com.ivocoelho.nomeapp.data.util

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.ivocoelho.nomeapp.data.exttension.calcularIdade
import java.text.SimpleDateFormat


class IdadeAutomatica {

    fun calcula(et: EditText): TextWatcher {
        return object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.length == 10) {
                    val date = SimpleDateFormat("dd/MM/yyyy").parse(s.toString())
                    val idade = date.calcularIdade()
                    val idadeAnos =  StringBuilder()
                    idadeAnos.append(idade)
                    idadeAnos.append(" anos")
                    et.setText(idadeAnos.toString())
                }
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int, count: Int, after: Int
            ) {
            }

            override fun afterTextChanged(s: Editable) {}
        }
    }


}