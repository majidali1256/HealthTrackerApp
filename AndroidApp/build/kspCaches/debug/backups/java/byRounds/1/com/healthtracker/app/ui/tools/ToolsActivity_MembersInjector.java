package com.healthtracker.app.ui.tools;

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
public final class ToolsActivity_MembersInjector implements MembersInjector<ToolsActivity> {
  private final Provider<FirebaseAuth> firebaseAuthProvider;

  public ToolsActivity_MembersInjector(Provider<FirebaseAuth> firebaseAuthProvider) {
    this.firebaseAuthProvider = firebaseAuthProvider;
  }

  public static MembersInjector<ToolsActivity> create(Provider<FirebaseAuth> firebaseAuthProvider) {
    return new ToolsActivity_MembersInjector(firebaseAuthProvider);
  }

  @Override
  public void injectMembers(ToolsActivity instance) {
    injectFirebaseAuth(instance, firebaseAuthProvider.get());
  }

  @InjectedFieldSignature("com.healthtracker.app.ui.tools.ToolsActivity.firebaseAuth")
  public static void injectFirebaseAuth(ToolsActivity instance, FirebaseAuth firebaseAuth) {
    instance.firebaseAuth = firebaseAuth;
  }
}
