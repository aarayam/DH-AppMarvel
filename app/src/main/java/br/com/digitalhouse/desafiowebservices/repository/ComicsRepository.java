package br.com.digitalhouse.desafiowebservices.repository;

import br.com.digitalhouse.desafiowebservices.model.ComicsResponse;
import io.reactivex.Single;

import static br.com.digitalhouse.desafiowebservices.network.RetrofitService.PRIVATE_KEY;
import static br.com.digitalhouse.desafiowebservices.network.RetrofitService.PUBLIC_KEY;
import static br.com.digitalhouse.desafiowebservices.network.RetrofitService.getApiService;
import static br.com.digitalhouse.desafiowebservices.util.AppUtil.md5;

public class ComicsRepository {

    public Single<ComicsResponse> getComics() {
        String ts = Long.toString(System.currentTimeMillis() / 1000);
        String hash = md5(ts + PRIVATE_KEY + PUBLIC_KEY);
        return getApiService().getComics("magazine", "comic", true, "focDate", "50", ts, hash, PUBLIC_KEY);
    }
}
