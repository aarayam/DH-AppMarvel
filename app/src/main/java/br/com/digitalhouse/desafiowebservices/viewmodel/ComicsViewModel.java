package br.com.digitalhouse.desafiowebservices.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import br.com.digitalhouse.desafiowebservices.model.Result;
import br.com.digitalhouse.desafiowebservices.repository.ComicsRepository;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ComicsViewModel extends AndroidViewModel {

    private MutableLiveData<List<Result>> resultLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private CompositeDisposable disposable = new CompositeDisposable();
    private ComicsRepository repository = new ComicsRepository();

    // Construtor padrão do viewmodel
    public ComicsViewModel(@NonNull Application application) {
        super(application);
    }


    public LiveData<List<Result>> getResults() {
        return resultLiveData;
    }

    public LiveData<Boolean> isLoasing(){
        return loading;
    }

    public void getComics() {

        disposable.add(repository.getComics()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((Disposable disposable1) -> {
                    loading.setValue(true);
                })
                .doAfterTerminate(() -> loading.setValue(false))
                .subscribe(response -> {
                    // Chegou aqui então alteramos o live data, assim a View que está observando ele pode atualizar a tela
                    resultLiveData.setValue(response.getData().getResults());
                }, throwable -> {
                    Log.i("LOG", "Error: " + throwable.getMessage());
                }));
    }
}
