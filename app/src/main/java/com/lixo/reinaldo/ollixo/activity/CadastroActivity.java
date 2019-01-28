package com.lixo.reinaldo.ollixo.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.lixo.reinaldo.ollixo.R;
import com.lixo.reinaldo.ollixo.helper.ConfiguracaoFirebase;

public class CadastroActivity extends AppCompatActivity {

    private Button botaoAcessar;
    private EditText campoEmail, campoSenha;
    private Switch tipoAcesso;

    //firebase
    private FirebaseAuth autenticacao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        inicializaComponentes();
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();

        botaoAcessar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = campoEmail.getText().toString();
                String senha = campoSenha.getText().toString();

                //Caso o email não estiver vazio
                if( !email.isEmpty()){
                    if(!senha.isEmpty()){

                        //Caso o estado do switch esteja checado

                        if(tipoAcesso.isChecked()){ //CADASTRO
                                autenticacao.createUserWithEmailAndPassword(

                                        email,senha


                                ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete( Task<AuthResult> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(CadastroActivity.this, "Cadastro realizado com sucesso !",
                                                    Toast.LENGTH_SHORT).show();
                                        }else{
                                           String erroExcessao = "";

                                           try {

                                               throw task.getException();

                                           }catch (FirebaseAuthWeakPasswordException e) {
                                                erroExcessao = " Digite uma senha mais forte!";
                                           }catch (FirebaseAuthInvalidCredentialsException e){
                                                erroExcessao = " Por favor , digite um email válido";
                                           }catch (FirebaseAuthUserCollisionException e){
                                                erroExcessao = " Esta conta já esta cadastrada";
                                        }catch (Exception e){
                                                erroExcessao = " Ao cadastrar usuário: "+e.getMessage();
                                                e.printStackTrace();
                                           }
                                            Toast.makeText(CadastroActivity.this, "Erro:"+erroExcessao,
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                        }else{//LOGIN, caso o switch nao esteja marcado
                            autenticacao.signInWithEmailAndPassword(
                                    email,senha
                            ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(CadastroActivity.this, "Logado com sucesso!",
                                                Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(),AnunciosActivity.class));

                                    }else{
                                        Toast.makeText(CadastroActivity.this, "Erro ao fazer login "+task.getException(),
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        }

                    }else{
                        Toast.makeText(CadastroActivity.this,"Preencha a senha!",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(CadastroActivity.this,"Preencha o E-mail",Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    private void inicializaComponentes(){
        campoEmail = findViewById(R.id.editCadastroEmail);
        campoSenha = findViewById(R.id.editCadastroSenha);
        botaoAcessar = findViewById(R.id.buttonAcesso);
        tipoAcesso = findViewById(R.id.switchAcesso);
    }
}
