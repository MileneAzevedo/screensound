package br.com.alura.Screensound.service;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;

public class ConsultaGemini {

    public ConsultaGemini(String artistaEscolhido) {
    }

    public static String obterSobre(String artistaEscolhido) {

        ChatLanguageModel gemini = GoogleAiGeminiChatModel.builder()
                .apiKey(System.getenv("GEMINI_APIKEY"))
                .modelName("gemini-1.5-flash")
                .build();

        String response = gemini.generate("Breve texto em português sobre" + artistaEscolhido);
        return response;
    }
}
