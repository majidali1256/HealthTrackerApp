package com.healthtracker.app.di;

import android.content.Context;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class FirebaseModule_ProvideGoogleSignInClientFactory implements Factory<GoogleSignInClient> {
  private final Provider<Context> contextProvider;

  private final Provider<GoogleSignInOptions> gsoProvider;

  public FirebaseModule_ProvideGoogleSignInClientFactory(Provider<Context> contextProvider,
      Provider<GoogleSignInOptions> gsoProvider) {
    this.contextProvider = contextProvider;
    this.gsoProvider = gsoProvider;
  }

  @Override
  public GoogleSignInClient get() {
    return provideGoogleSignInClient(contextProvider.get(), gsoProvider.get());
  }

  public static FirebaseModule_ProvideGoogleSignInClientFactory create(
      Provider<Context> contextProvider, Provider<GoogleSignInOptions> gsoProvider) {
    return new FirebaseModule_ProvideGoogleSignInClientFactory(contextProvider, gsoProvider);
  }

  public static GoogleSignInClient provideGoogleSignInClient(Context context,
      GoogleSignInOptions gso) {
    return Preconditions.checkNotNullFromProvides(FirebaseModule.INSTANCE.provideGoogleSignInClient(context, gso));
  }
}
