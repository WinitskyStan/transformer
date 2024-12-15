package org.s2n.csv.exceptions;

public class TransformCsvRowException extends RuntimeException {

    public TransformCsvRowException(Throwable e) {
        super(e);
    }

    public TransformCsvRowException(String message) {
        super(message);
    }

}
