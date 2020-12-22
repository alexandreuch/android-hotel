package com.trab.hotel;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class Auxiliares {
    public int verificaRetorna(String tipo, int pessoas, int dias) {
        int adicional = 0;
        if (tipo.equals("single") && pessoas <= 2) return (50 * dias) + (10 * (pessoas - 1));
        else if (tipo.equals("double") && pessoas <= 4) {
            if ((pessoas - 2) > 1) adicional = (pessoas - 2);
            return (60 * dias) + (20 * adicional);
        } else if (tipo.equals("suite") && pessoas <= 6) {
            if ((pessoas - 3) > 1) adicional = (pessoas - 3);
            return (70 * dias) + (30 * adicional);
        } else return -1;
    }

    public boolean verificaData(String data) { /** Verifica se a data é válida */
        if (data.length() == 8) {
            String dia = data.substring(0, 2);
            String mes = data.substring(3, 5);
            String ano = data.substring(6, 8);

            boolean bdia = (dia != null && dia.matches("[0-9]*") && dia.equals("") == false);
            boolean bmes = (mes != null && mes.matches("[0-9]*") && mes.equals("") == false);
            boolean bano = (ano != null && ano.matches("[0-9]*") && ano.equals("") == false);

            if ((bdia && bmes && bano) == true) {
                int vdia = Integer.parseInt(dia);
                int vmes = Integer.parseInt(mes);
                int vano = Integer.parseInt(ano);
                if (vdia <= 30 && vmes <= 12 && vano <= 99) return true;
                else return false;
            } else return false;
        } else return false;
    }

    public String dataSaida(String dataEntrada, int noites) {
        int diaReal = Integer.parseInt(dataEntrada.substring(0, 2));
        int mesReal = Integer.parseInt(dataEntrada.substring(3, 5));
        int anoReal = Integer.parseInt(dataEntrada.substring(6, 8));
        int i = 0, j = 0;
        int bufferDia = diaReal + noites;
        int bufferMes = mesReal;
        int bufferAno = anoReal;
        if (bufferDia > 30) {
            while (bufferDia > 30) {
                bufferDia -= 30;
                i++;
            }
            bufferDia = (diaReal + noites) % 30;
            if (bufferDia == 0) {
                bufferDia += 1;
                i++;
            }
            bufferMes += i;
            if (bufferMes > 12) {
                while (bufferMes > 12) {
                    bufferMes -= 12;
                    j++;
                }
                bufferAno += j++;
            }
        }
        String bufferD = "";
        String bufferM = "";
        String bufferA = "";
        if(bufferDia < 10) bufferD = "0"+bufferDia;
        else bufferD += bufferDia;
        if(bufferMes < 10) bufferM = "0"+bufferMes;
        else bufferM += bufferMes;
        if(bufferAno < 10) bufferA = "0"+bufferAno;
        else bufferA += bufferAno;

        return bufferD + "/" + bufferM + "/" + bufferA;
    }

    public int diaReserva(String dataEntrada) {
        int diaReal = Integer.parseInt(dataEntrada.substring(0, 2));
        int mesReal = Integer.parseInt(dataEntrada.substring(3, 5));
        int anoReal = Integer.parseInt(dataEntrada.substring(6, 8));
        mesReal *= 30;
        anoReal *= 365;
        return diaReal + mesReal + anoReal;
    }

    public List<String> partirClientes(String clientes) {
        List<String> error = new ArrayList<String>();
        error.add("ERROR");
        List<String> resultado = new ArrayList<String>();
        String buffer = "";
        for (int i = 0; i < clientes.length(); i++) {
            if (clientes.charAt(i) != ',') buffer += clientes.charAt(i);
            else {
                if (resultado.contains(buffer)) return error;
                else if (buffer != null && buffer.matches("[0-9]*") && !buffer.equals("")) {
                    resultado.add(buffer);
                    buffer = "";
                } else return error;
            }
        }
        if (resultado.contains(buffer)) return error;
        else if (buffer != null && buffer.matches("[0-9]*") && !buffer.equals("")) {
            resultado.add(buffer);
            return resultado;
        } else return error;
    }

    public String transfomarClientes(List<String> clientes) {
        String resultado = "";
        for (int i = 0; i < clientes.size(); i++) {
            resultado += clientes.get(i);
            if (i != clientes.size() - 1) resultado += ",";
        }
        return resultado;
    }
}