import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main implements ActionListener {
    int resultado = 0;
    String operador = "";
    int primerNumero = 0;
    boolean nuevoNumero = true;
    JLabel resultadoLabel;
    StringBuilder historial = new StringBuilder();

    public Main() {
        JFrame frame = new JFrame("Calculadora");
        JPanel mainPanel = new JPanel();
        JPanel panel = new JPanel();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainPanel.setLayout(new BorderLayout());
        panel.setLayout(new GridLayout(5, 4));

        resultadoLabel = new JLabel(String.valueOf((int) resultado));
        resultadoLabel.setOpaque(true);
        resultadoLabel.setBackground(Color.BLACK);
        resultadoLabel.setForeground(Color.WHITE);
        resultadoLabel.setHorizontalAlignment(JLabel.CENTER);
        resultadoLabel.setVerticalAlignment(JLabel.CENTER);
        resultadoLabel.setFont(new Font("Cascadia Code PL", Font.PLAIN, 24));
        resultadoLabel.setPreferredSize(new Dimension(resultadoLabel.getPreferredSize().width, 100));
        mainPanel.add(resultadoLabel, BorderLayout.NORTH);

        String[] textosBotones = {
                "C", "M", "", "/",
                "7", "8", "9", "x",
                "4", "5", "6", "-",
                "1", "2", "3", "+",
                "", "0", "", "="
        };

        Font buttonFont = new Font("Cascadia Code PL", Font.PLAIN, 24);

        JButton[] botones = new JButton[20];

        for (int i = 0; i < 20; i++) {
            botones[i] = new JButton(textosBotones[i]);
            botones[i].setBackground(Color.BLACK);
            botones[i].setForeground(Color.WHITE);
            botones[i].setFont(buttonFont);
            botones[i].addActionListener(this);
            panel.add(botones[i]);
        }

        mainPanel.add(panel, BorderLayout.CENTER);
        frame.add(mainPanel);
        frame.setSize(300, 400);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        switch (comando) {
            case "C":
                resetearCalculadora();
                break;
            case "M":
                mostrarHistorial();
                break;
            case "+":
            case "-":
            case "x":
            case "/":
                if (!nuevoNumero) {
                    calcular();
                    historial.append(primerNumero).append(" ").append(operador).append(" ").append(resultado).append(" = ").append(resultado).append("\n");
                }
                operador = comando.equals("x") ? "*" : comando;
                primerNumero = resultado;
                nuevoNumero = true;
                break;
            case "=":
                calcular();
                historial.append(primerNumero).append(" ").append(operador).append(" ").append(resultado).append(" = ").append(resultado).append("\n");
                operador = "";
                nuevoNumero = true;
                break;
            case "%":
                if (!nuevoNumero) {
                    resultado = primerNumero * (resultado / 100);
                    historial.append(primerNumero).append(" % = ").append(resultado).append("\n");
                    nuevoNumero = true;
                } else {
                    primerNumero = resultado;
                }
                break;
            case "":
                break;
            default:
                if (comando.equals(".")) {
                    if (nuevoNumero) {
                        resultadoLabel.setText("0" + comando);
                        nuevoNumero = false;
                    } else if (!resultadoLabel.getText().contains(".")) {
                        resultadoLabel.setText(resultadoLabel.getText() + comando);
                    }
                } else {
                    if (nuevoNumero) {
                        resultadoLabel.setText(comando);
                        nuevoNumero = false;
                    } else {
                        resultadoLabel.setText(resultadoLabel.getText() + comando);
                    }
                }
                resultado = Integer.parseInt(resultadoLabel.getText());
                break;
        }

        resultadoLabel.setText(String.valueOf(resultado));
    }

    private void calcular() {
        switch (operador) {
            case "+":
                resultado = primerNumero + resultado;
                break;
            case "-":
                resultado = primerNumero - resultado;
                break;
            case "x":
            case "*":
                resultado = primerNumero * resultado;
                break;
            case "/":
                if (resultado != 0) {
                    resultado = primerNumero / resultado;
                } else {
                    resultadoLabel.setText("Error");
                }
                break;
        }
    }

    private void resetearCalculadora() {
        resultado = 0;
        primerNumero = 0;
        operador = "";
        nuevoNumero = true;
        resultadoLabel.setText(String.valueOf((int) resultado));
    }

    private void mostrarHistorial() {
        JOptionPane.showMessageDialog(null, historial.toString(), "Historial de Operaciones", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        new Main();
    }
}
