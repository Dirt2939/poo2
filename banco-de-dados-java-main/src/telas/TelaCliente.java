package telas;

import java.sql.*;
import dal.Mod_conexao;
import javax.swing.JOptionPane;

public class TelaCliente extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    public TelaCliente() {
        initComponents();
        conexao = Mod_conexao.conector();
        mnStatus.setSelectedIndex(-1);
    }

    private void consultar() {
        String sql = "SELECT * FROM tb_cliente WHERE id=?";

        if (!idValido()) {
            return;
        }

        try (PreparedStatement consulta = conexao.prepareStatement(sql)) {
            consulta.setInt(1, Integer.parseInt(txtId.getText().trim()));
            rs = consulta.executeQuery();

            if (rs.next()) {
                txtNome.setText(rs.getString("nome"));
                txtEndereco.setText(rs.getString("endereco"));
                txtCidade.setText(rs.getString("cidade"));
                txtUf.setText(rs.getString("uf"));
                txtDocumento.setText(rs.getString("documento"));
                txtFone.setText(rs.getString("fone"));
                txtDataNasc.setText(rs.getString("data_nascimento"));
                if (rs.getString("documento").contains("/")) {
                    mnStatus.setSelectedIndex(1);
                } else {
                    mnStatus.setSelectedIndex(0);
                }
            } else {
                JOptionPane.showMessageDialog(null, "CLIENTE NÃO CADASTRADO...");
                limparCampos(false);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao consultar cliente: " + e.getMessage());
        }
    }

    private void adicionar() {
        String sql = "INSERT INTO tb_cliente (nome,endereco,cidade,uf,documento,fone,data_nascimento) "
                + "VALUES (?,?,?,?,?,?,?)";
        if (!camposClienteValidos()) {
            return;
        }
        try (PreparedStatement inclusao = conexao.prepareStatement(sql)) {
            preencherCliente(inclusao, false);

            int adicionado = inclusao.executeUpdate();

            if (adicionado > 0) {
                JOptionPane.showMessageDialog(null, "Cliente Cadastrado");
                //      Exibe a msg caso inserido com sucesso
                //      as linhas abaixo irão limpar o formulario
                limparCampos(true);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao adicionar cliente: " + e.getMessage());
        } finally {
        }

    }

    private void alterar() {
        String sql = "UPDATE tb_cliente SET nome=?, endereco=?, cidade=?, uf=?, documento=?, fone=?, data_nascimento=? WHERE id=?";
        if (!idValido() || !camposClienteValidos()) {
            return;
        }
        try (PreparedStatement atualizacao = conexao.prepareStatement(sql)) {
            preencherCliente(atualizacao, true);

            int adicionado = atualizacao.executeUpdate();
            // Retorna 1 se OK
            if (adicionado > 0) {
                JOptionPane.showMessageDialog(null, "CLIENTE ALTERADO COM SUCESSO");
            } else {
                JOptionPane.showMessageDialog(null, "Cliente não encontrado.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao alterar cliente: " + e.getMessage());
        }
    }

    private void apagar() {
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir este cliente?", "ATENÇÃO", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "DELETE FROM tb_cliente WHERE id = ?";
            if (!idValido()) {
                return;
            }
            try (PreparedStatement exclusao = conexao.prepareStatement(sql)) {
                exclusao.setInt(1, Integer.parseInt(txtId.getText().trim()));
                int apagado = exclusao.executeUpdate();
                if (apagado > 0) {
                    JOptionPane.showMessageDialog(null, "CLIENTE APAGADO");
                    limparCampos(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Cliente não encontrado.");
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao apagar cliente: " + e.getMessage());
            }
        }
    }

    private boolean idValido() {
        try {
            if (Integer.parseInt(txtId.getText().trim()) > 0) {
                return true;
            }
        } catch (NumberFormatException ignored) {
        }
        JOptionPane.showMessageDialog(null, "Informe um ID numérico válido.");
        return false;
    }

    private boolean camposClienteValidos() {
        if (txtNome.getText().trim().isEmpty() || txtEndereco.getText().trim().isEmpty()
                || txtCidade.getText().trim().isEmpty() || txtUf.getText().trim().isEmpty()
                || txtFone.getText().trim().isEmpty()
                || txtDataNasc.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Preencha todos os campos do cliente.");
            return false;
        }
        if (txtUf.getText().trim().length() != 2) {
            JOptionPane.showMessageDialog(null, "UF deve ter exatamente duas letras.");
            return false;
        }
        if (!validarDocumento(txtDocumento.getText().trim())) {
            JOptionPane.showMessageDialog(null, "Documento inválido.");
            return false;
        }
        return true;
    }

    private boolean validarDocumento(String doc) {
        String cpfRegex = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}";
        String cnpjRegex = "\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}";

        return doc != null && (doc.matches(cpfRegex) || doc.matches(cnpjRegex));
    }

    private void preencherCliente(PreparedStatement comando, boolean incluirId) throws SQLException {
        comando.setString(1, txtNome.getText().trim());
        comando.setString(2, txtEndereco.getText().trim());
        comando.setString(3, txtCidade.getText().trim());
        comando.setString(4, txtUf.getText().trim().toUpperCase());
        comando.setString(5, txtDocumento.getText().trim());
        comando.setString(6, txtFone.getText().trim());
        comando.setString(7, txtDataNasc.getText().trim());
        if (incluirId) {
            comando.setInt(8, Integer.parseInt(txtId.getText().trim()));
        }
    }

    private void limparCampos(boolean limparId) {
        if (limparId) {
            txtId.setText(null);
        }
        txtNome.setText(null);
        txtEndereco.setText(null);
        txtCidade.setText(null);
        txtUf.setText(null);
        txtDocumento.setText(null);
        txtFone.setText(null);
        txtDataNasc.setText(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        rdgStatus = new javax.swing.ButtonGroup();
        ID = new javax.swing.JLabel();
        nome = new javax.swing.JLabel();
        email = new javax.swing.JLabel();
        senha = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        txtNome = new javax.swing.JTextField();
        txtEndereco = new javax.swing.JTextField();
        txtCidade = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        btnAdd = new javax.swing.JButton();
        btnVisualizar = new javax.swing.JButton();
        btnApagar = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        txtUf = new javax.swing.JTextField();
        txtDocumento = new javax.swing.JTextField();
        txtFone = new javax.swing.JTextField();
        txtDataNasc = new javax.swing.JTextField();
        senha1 = new javax.swing.JLabel();
        nome1 = new javax.swing.JLabel();
        email1 = new javax.swing.JLabel();
        ID1 = new javax.swing.JLabel();
        mnStatus = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);

        ID.setText("ID:");

        nome.setText("Endereço:");

        email.setText("Nome:");

        senha.setText("Cidade:");

        txtId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdActionPerformed(evt);
            }
        });

        txtNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomeActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("TELA CADASTRO");

        btnAdd.setText("Adicionar");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnVisualizar.setText("Visualizar");
        btnVisualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVisualizarActionPerformed(evt);
            }
        });

        btnApagar.setText("Apagar");
        btnApagar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnApagarActionPerformed(evt);
            }
        });

        btnEdit.setText("Editar");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        txtUf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUfActionPerformed(evt);
            }
        });

        txtDocumento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDocumentoActionPerformed(evt);
            }
        });

        senha1.setText("Data Nasc");

        nome1.setText("fone:");

        email1.setText("DOC:");

        ID1.setText("UF:");

        mnStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "PF", "PJ" }));

        jLabel3.setText("Status:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(ID, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(230, 230, 230)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(senha1)
                                    .addComponent(jLabel3))
                                .addGap(28, 28, 28)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(mnStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(125, 125, 125)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(btnVisualizar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(btnAdd)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnApagar, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addComponent(txtDataNasc, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(nome1)
                                    .addComponent(email1)
                                    .addComponent(ID1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(senha)
                                    .addComponent(nome)
                                    .addComponent(email))
                                .addGap(29, 29, 29)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtDocumento, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 373, Short.MAX_VALUE)
                                    .addComponent(txtUf, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtCidade, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtEndereco, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtNome, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtId, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtFone))
                                .addGap(7, 7, 7)))))
                .addContainerGap(51, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel1)
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ID)
                    .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(email))
                    .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(nome))
                    .addComponent(txtEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(senha)
                    .addComponent(txtCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(ID1))
                    .addComponent(txtUf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(email1))
                    .addComponent(txtDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(nome1))
                    .addComponent(txtFone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(senha1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(txtDataNasc, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(mnStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnApagar)
                            .addComponent(btnAdd))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEdit)
                    .addComponent(btnVisualizar))
                .addGap(25, 25, 25))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomeActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_txtNomeActionPerformed

    private void txtIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdActionPerformed

    private void btnVisualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVisualizarActionPerformed
        // TODO add your handling code here:
        consultar();
    }//GEN-LAST:event_btnVisualizarActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
        adicionar();
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        // TODO add your handling code here:
        alterar();
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnApagarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnApagarActionPerformed
        // TODO add your handling code here:
        apagar();
    }//GEN-LAST:event_btnApagarActionPerformed

    private void txtUfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUfActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUfActionPerformed

    private void txtDocumentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDocumentoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDocumentoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel ID;
    private javax.swing.JLabel ID1;
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnApagar;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnVisualizar;
    private javax.swing.JLabel email;
    private javax.swing.JLabel email1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JComboBox<String> mnStatus;
    private javax.swing.JLabel nome;
    private javax.swing.JLabel nome1;
    private javax.swing.ButtonGroup rdgStatus;
    private javax.swing.JLabel senha;
    private javax.swing.JLabel senha1;
    private javax.swing.JTextField txtCidade;
    private javax.swing.JTextField txtDataNasc;
    private javax.swing.JTextField txtDocumento;
    private javax.swing.JTextField txtEndereco;
    private javax.swing.JTextField txtFone;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtNome;
    private javax.swing.JTextField txtUf;
    // End of variables declaration//GEN-END:variables
}
