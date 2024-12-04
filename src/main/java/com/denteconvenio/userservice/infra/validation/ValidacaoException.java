
package com.denteconvenio.userservice.infra.validation;


public class ValidacaoException extends RuntimeException{

    public ValidacaoException(String mensagem){
        super(mensagem);
    }
}