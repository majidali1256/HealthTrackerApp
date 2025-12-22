package com.healthtracker.app.ui.food;

import com.google.firebase.auth.FirebaseAuth;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation"
})
public final class FoodLoggerActivity_MembersInjector implements MembersInjector<FoodLoggerActivity> {
  private final Provider<FirebaseAuth> firebaseAuthProvider;

  public FoodLoggerActivity_MembersInjector(Provider<FirebaseAuth> firebaseAuthProvider) {
    this.firebaseAuthProvider = firebaseAuthProvider;
  }

  public static MembersInjector<FoodLoggerActivity> create(
      Provider<FirebaseAuth> firebaseAuthProvider) {
    return new FoodLoggerActivity_MembersInjector(firebaseAuthProvider);
  }

  @Override
  public void injectMembers(FoodLoggerActivity instance) {
    injectFirebaseAuth(instance, firebaseAuthProvider.get());
  }

  @InjectedFieldSignature("com.healthtracker.app.ui.food.FoodLoggerActivity.firebaseAuth")
  public static void injectFirebaseAuth(FoodLoggerActivity instance, FirebaseAuth firebaseAuth) {
    instance.firebaseAuth = firebaseAuth;
  }
}
