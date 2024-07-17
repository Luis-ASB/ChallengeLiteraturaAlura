package com.literatura.challenger_literatura.service;

public interface IDataConverter {
    <T> T getData(String json, Class<T> clase);
}
