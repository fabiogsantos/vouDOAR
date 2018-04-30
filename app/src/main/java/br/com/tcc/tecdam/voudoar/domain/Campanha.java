package br.com.tcc.tecdam.voudoar.domain;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.UUID;

/**
 * Created by fabio.goncalves on 12/04/2018.
 */

public class Campanha implements Parcelable{

    public static final String TABLE_NAME = "Campanhas";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_DATA_INCLUSAO = "data_inclusao";
    public static final String COLUMN_TIPO = "tipo";
    public static final String COLUMN_TITULO = "titulo";
    public static final String COLUMN_FRASE = "frase";
    public static final String COLUMN_DATA_INICIO = "data_inicio";
    public static final String COLUMN_DATA_FINAL = "data_final";
    public static final String COLUMN_OBJETIVO = "objetivo";
    public static final String COLUMN_ATIVIDADES = "atividades";
    public static final String COLUMN_PUBLICO_ALVO = "publico_alvo";
    public static final String COLUMN_UF_ATUACAO = "uf_atuacao";
    public static final String COLUMN_AREA_ATUACAO = "area_atuacao";
    public static final String COLUMN_IMAGEM = "imagem";
    public static final String COLUMN_COR_FUNDO = "cor_fundo";
    public static final String COLUMN_SOBRE_PROBLEMA = "sobre_problema";
    public static final String COLUMN_SOBRE_SOLUCAO = "sobre_solucao";
    public static final String SQL_DROP_TABLE = "DROP TABLE "+TABLE_NAME;
    public static final String SQL_CREATE_TABLE = "CREATE TABLE "+ TABLE_NAME +"("+
            COLUMN_ID + " CHAR(36) PRIMARY KEY," +
            COLUMN_DATA_INCLUSAO + " DATETIME DEFAULT CURRENT_TIMESTAMP," +
            COLUMN_TIPO + " INTEGER NOT NULL," +
            COLUMN_TITULO + " TEXT NOT NULL," +
            COLUMN_FRASE + " TEXT," +
            COLUMN_DATA_INICIO + " INTEGER," +
            COLUMN_DATA_FINAL + " INTEGER," +
            COLUMN_OBJETIVO + " TEXT," +
            COLUMN_ATIVIDADES + " TEXT," +
            COLUMN_PUBLICO_ALVO + " TEXT," +
            COLUMN_UF_ATUACAO + " TEXT," +
            COLUMN_AREA_ATUACAO + " TEXT," +
            COLUMN_IMAGEM + " TEXT," +
            COLUMN_COR_FUNDO + " TEXT," +
            COLUMN_SOBRE_PROBLEMA + " TEXT," +
            COLUMN_SOBRE_SOLUCAO + " TEXT" +
            ");";

    private String  id = "";                 // Identificador gerado automaticamente pelo banco de dados
    private Integer tipo = 0;                // Causa de interesse da campanha: 0 - Outros, 1 - Capacitação Profissional, 2 - Cultura e Arte, 3 - Defesa dos Direitos Humanos, 4 - Educação, 5 - Esportes, 6 - Meio Ambiente e Proteção dos Animais, 7 - Saúde, 8 - Serviços Sociais
    private String  titulo = "";             // Nome da campanha
    private String  frase = "";              // Frase de impácto ou Bordão
    private Date    dataInicio;              // Data de Início da Campanha
    private Date    dataFinal;               // Data Final da Campanha
    private String  objetivo = "";           // Sobre nós (Motivação e Objetivos)
    private String  atividades = "";         // ações realizadas na campanha
    private String  publicoAlvo = "";        // Quais pessoas deseja atender
    private String  ufAtuacao = "";          // Estado de atuação da campanha;
    private String  areaAtuacao = "";        // Cidade de atuação da campanha;
    private String  imagem = "";             // Logotipo
    private String  corFundo = "";           // Cor de Fundo
    private String  sobreProblema = "";      // Sobre o problema que a campanha pretende atacar
    private String  sobreSolucao = "";       // Sobre a solução proposta para atacar o problema

    public Campanha() {
    }

    public Campanha(int tipo, String titulo, Date dataInicio) {
        this.tipo = tipo;
        this.titulo = titulo;
        this.dataInicio = dataInicio;
    }

    public Campanha(String id, int tipo, String titulo, String frase, Date dataInicio, Date dataFinal,
                    String objetivo, String atividades, String publicoAlvo, String ufAtuacao,
                    String areaAtuacao, String imagem, String corFundo, String sobreProblema,
                    String sobreSolucao) {
        this.id            = id;
        this.tipo          = tipo;
        this.titulo        = titulo;
        this.frase         = frase;
        this.dataInicio    = dataInicio;
        this.dataFinal     = dataFinal;
        this.objetivo      = objetivo;
        this.atividades    = atividades;
        this.publicoAlvo   = publicoAlvo;
        this.ufAtuacao     = ufAtuacao;
        this.areaAtuacao   = areaAtuacao;
        this.imagem        = imagem;
        this.corFundo      = corFundo;
        this.sobreProblema = sobreProblema;
        this.sobreSolucao  = sobreSolucao;
    }

    public Campanha(Cursor cursor) {
        this.id            = cursor.getString(cursor.getColumnIndex(COLUMN_ID));
        this.tipo          = cursor.getInt(cursor.getColumnIndex(COLUMN_TIPO));
        this.titulo        = cursor.getString(cursor.getColumnIndex(COLUMN_TITULO));
        this.frase         = cursor.getString(cursor.getColumnIndex(COLUMN_FRASE));
        this.dataInicio    = new Date(cursor.getLong(cursor.getColumnIndex(COLUMN_DATA_INICIO)));
        this.dataFinal     = new Date(cursor.getLong(cursor.getColumnIndex(COLUMN_DATA_FINAL)));
        this.objetivo      = cursor.getString(cursor.getColumnIndex(COLUMN_OBJETIVO));
        this.atividades    = cursor.getString(cursor.getColumnIndex(COLUMN_ATIVIDADES));
        this.publicoAlvo   = cursor.getString(cursor.getColumnIndex(COLUMN_PUBLICO_ALVO));
        this.ufAtuacao     = cursor.getString(cursor.getColumnIndex(COLUMN_UF_ATUACAO));
        this.areaAtuacao   = cursor.getString(cursor.getColumnIndex(COLUMN_AREA_ATUACAO));
        this.imagem        = cursor.getString(cursor.getColumnIndex(COLUMN_IMAGEM));
        this.corFundo      = cursor.getString(cursor.getColumnIndex(COLUMN_COR_FUNDO));
        this.sobreProblema = cursor.getString(cursor.getColumnIndex(COLUMN_SOBRE_PROBLEMA));
        this.sobreSolucao  = cursor.getString(cursor.getColumnIndex(COLUMN_SOBRE_SOLUCAO));
    }

    protected Campanha(Parcel in) {
        id = in.readString();
        if (in.readByte() == 0) {
            tipo = null;
        } else {
            tipo = in.readInt();
        }
        titulo = in.readString();
        frase = in.readString();
        objetivo = in.readString();
        atividades = in.readString();
        publicoAlvo = in.readString();
        ufAtuacao = in.readString();
        areaAtuacao = in.readString();
        imagem = in.readString();
        corFundo = in.readString();
        sobreProblema = in.readString();
        sobreSolucao = in.readString();
    }

    public static final Creator<Campanha> CREATOR = new Creator<Campanha>() {
        @Override
        public Campanha createFromParcel(Parcel in) {
            return new Campanha(in);
        }

        @Override
        public Campanha[] newArray(int size) {
            return new Campanha[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getFrase() {
        return frase;
    }

    public void setFrase(String frase) {
        this.frase = frase;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getCorFundo() {
        return corFundo;
    }

    public void setCorFundo(String corFundo) {
        this.corFundo = corFundo;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(Date dataFinal) {
        this.dataFinal = dataFinal;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public String getAtividades() {
        return atividades;
    }

    public void setAtividades(String atividades) {
        this.atividades = atividades;
    }

    public String getSobreProblema() {
        return sobreProblema;
    }

    public void setSobreProblema(String sobreProblema) {
        this.sobreProblema = sobreProblema;
    }

    public String getSobreSolucao() {
        return sobreSolucao;
    }

    public void setSobreSolucao(String sobreSolucao) {
        this.sobreSolucao = sobreSolucao;
    }

    public String getPublicoAlvo() {
        return publicoAlvo;
    }

    public void setPublicoAlvo(String publicoAlvo) {
        this.publicoAlvo = publicoAlvo;
    }

    public String getUfAtuacao() {
        return ufAtuacao;
    }

    public void setUfAtuacao(String ufAtuacao) {
        this.ufAtuacao = ufAtuacao;
    }

    public String getAreaAtuacao() {
        return areaAtuacao;
    }

    public void setAreaAtuacao(String areaAtuacao) {
        this.areaAtuacao = areaAtuacao;
    }

    @Override
    public String toString() {
        return titulo + " (Dt.Inicio: " + dataInicio + ")";
    }

    public ContentValues GetContentValues() {
        ContentValues dados = new ContentValues();

        if (this.getId().isEmpty()) {
          this.setId(UUID.randomUUID().toString());
        }
        dados.put(COLUMN_ID, this.getId());

        if (this.getTipo() != null) {
            dados.put(COLUMN_TIPO, this.getTipo());
        }
        dados.put(COLUMN_TITULO, this.getTitulo() );
        dados.put(COLUMN_FRASE, this.getFrase() );
        if (this.getDataInicio() != null) {
            dados.put(COLUMN_DATA_INICIO,  this.getDataInicio().getTime() );
        }
        if (this.getDataFinal() != null) {
            dados.put(COLUMN_DATA_FINAL, this.getDataFinal().getTime());
        }
        dados.put(COLUMN_OBJETIVO, this.getObjetivo() );
        dados.put(COLUMN_ATIVIDADES, this.getAtividades() );
        dados.put(COLUMN_PUBLICO_ALVO, this.getPublicoAlvo() );
        dados.put(COLUMN_UF_ATUACAO, this.getUfAtuacao() );
        dados.put(COLUMN_AREA_ATUACAO, this.getAreaAtuacao() );
        dados.put(COLUMN_IMAGEM, this.getImagem() );
        dados.put(COLUMN_COR_FUNDO, this.getCorFundo() );
        dados.put(COLUMN_SOBRE_PROBLEMA, this.getSobreProblema() );
        dados.put(COLUMN_SOBRE_SOLUCAO, this.getSobreSolucao() );
        return dados;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        if (tipo == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(tipo);
        }
        parcel.writeString(titulo);
        parcel.writeString(frase);
        parcel.writeString(objetivo);
        parcel.writeString(atividades);
        parcel.writeString(publicoAlvo);
        parcel.writeString(ufAtuacao);
        parcel.writeString(areaAtuacao);
        parcel.writeString(imagem);
        parcel.writeString(corFundo);
        parcel.writeString(sobreProblema);
        parcel.writeString(sobreSolucao);
    }
}
