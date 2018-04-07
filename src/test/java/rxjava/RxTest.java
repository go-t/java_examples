package rxjava;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import got.util.Jsons;
import got.util.Strings;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;

public class RxTest {

  public static enum Event {
    Next, Error, Complete, Success, Subscribe
  }

  public static class Recorder<T> implements Observer<T>, SingleObserver<T> {
    // public ArrayList<Item> items = new ArrayList<>();
    public ArrayList<Object> items = new ArrayList<>();
    public Disposable disposable;

    @Override
    public void onSubscribe(Disposable d) {
      items.add(Event.Subscribe);
      this.disposable = d;
    }

    @Override
    public void onNext(T t) {
      items.add(t);
    }

    @Override
    public void onSuccess(T t) {
      items.add(t);
    }

    @Override
    public void onError(Throwable e) {
      items.add(Event.Error);
    }

    @Override
    public void onComplete() {
      items.add(Event.Complete);
    }

    public void cancel() {
      if (disposable != null) {
        disposable.dispose();
      }
    }

    public String toString() {
      return Jsons.toJson(items);
    }
  }

  Recorder<Integer> recorder;

  @Before
  public void setup() {
    recorder = new Recorder<>();
  }

  // @After
  public void teardown() {
    System.err.println(Strings.toString(recorder.items));
  }

  @Test
  public void testSingle() {
    Single.just(1).subscribe(recorder);
    assertEquals(recorder.toString(), "[\"Subscribe\",1]");
  }

  @Test
  public void testFrom() {
    Observable.fromArray(1,2,3).subscribe(recorder);
    assertEquals(recorder.toString(), "[\"Subscribe\",1,2,3,\"Complete\"]");
  }

  @Test
  public void testCreate() {
    Observable.create((ObservableEmitter<Integer> e)->{
      e.onNext(1);
      e.onNext(2);
      e.onNext(3);
      e.onComplete();
    }).subscribe(recorder);
    assertEquals(recorder.toString(), "[\"Subscribe\",1,2,3,\"Complete\"]");
  }

  @Test
  public void testSubject() {
    PublishSubject<Integer> pub = PublishSubject.create(); 
    pub.serialize().subscribe(recorder);
    pub.onNext(1);
    pub.onNext(2);
    pub.onNext(3);
    pub.onComplete();
    assertEquals(recorder.toString(), "[\"Subscribe\",1,2,3,\"Complete\"]");
    recorder.cancel();
    assertFalse(pub.hasObservers());
  }

}