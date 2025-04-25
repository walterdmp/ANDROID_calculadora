package br.edu.ifsuldeminas.mch.calc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import de.congrace.exp4j.ExpressionBuilder;
import de.congrace.exp4j.Calculable;

public class MainActivity extends AppCompatActivity {

    private TextView textViewResultado;
    private TextView textViewUltimaExpressao;
    private StringBuilder expressao = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewResultado = findViewById(R.id.textViewResultadoID);
        textViewUltimaExpressao = findViewById(R.id.textViewUltimaExpressaoID);

        configurarBotao(R.id.buttonZeroID, "0");
        configurarBotao(R.id.buttonUmID, "1");
        configurarBotao(R.id.buttonDoisID, "2");
        configurarBotao(R.id.buttonTresID, "3");
        configurarBotao(R.id.buttonQuatroID, "4");
        configurarBotao(R.id.buttonCincoID, "5");
        configurarBotao(R.id.buttonSeisID, "6");
        configurarBotao(R.id.buttonSeteID, "7");
        configurarBotao(R.id.buttonOitoID, "8");
        configurarBotao(R.id.buttonNoveID, "9");

        configurarBotao(R.id.buttonSomaID, "+");
        configurarBotao(R.id.buttonSubtracaoID, "-");
        configurarBotao(R.id.buttonMultiplicacaoID, "x");
        configurarBotao(R.id.buttonDivisaoID, "/");
        configurarBotao(R.id.buttonPorcentoID, "%");
        configurarBotao(R.id.buttonVirgulaID, ".");


        Button botaoReset = findViewById(R.id.buttonResetID);
        botaoReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expressao.setLength(0);
                textViewResultado.setText("");
                textViewUltimaExpressao.setText("");
            }
        });

        Button botaoDelete = findViewById(R.id.buttonDeleteID);
        botaoDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (expressao.length() > 0) {
                    expressao.deleteCharAt(expressao.length() - 1);
                    textViewResultado.setText(expressao.toString());
                }
            }
        });

        Button botaoIgual = findViewById(R.id.buttonIgualID);
        botaoIgual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    textViewUltimaExpressao.setText(expressao.toString());

                    String expressaoTratada = expressao.toString().replace("%", "/100");
                    expressaoTratada = expressao.toString().replace("x", "*");

                    Calculable avaliador = new ExpressionBuilder(expressaoTratada).build();
                    double resultado = avaliador.calculate();

                    textViewResultado.setText(String.valueOf(resultado));

                    String result = String.valueOf(resultado);
                    expressao.setLength(0);
                    expressao.append(result);

                } catch (Exception e) {
                    textViewResultado.setText("Erro");
                    Log.d("Calculadora", "Erro ao calcular: " + e.getMessage());
                }
            }
        });
    }

    private void configurarBotao(int idBotao, final String valor) {
        Button botao = findViewById(idBotao);
        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tamanho = expressao.length();

                // Se já tem algo digitado, pega o último caractere
                char ultimo = tamanho > 0 ? expressao.charAt(tamanho - 1) : ' ';

                if ((valor.equals("+") || valor.equals("-") || valor.equals("x") || valor.equals("/") || valor.equals("%") || valor.equals(".")) &&
                        (ultimo == '+' || ultimo == '-' || ultimo == 'x' || ultimo == '/' || ultimo == '%' || ultimo == '.')) {
                    expressao.setCharAt(tamanho - 1, valor.charAt(0));
                    textViewResultado.setText(expressao.toString());
                    return;
                }

                expressao.append(valor);
                textViewResultado.setText(expressao.toString());
            }
        });
    }

}
