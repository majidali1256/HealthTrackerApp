package com.healthtracker.app.ui.tools;

import com.google.firebase.auth.FirebaseAuth;
import com.healthtracker.app.utils.PdfGenerator;
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

  private final Provider<PdfGenerator> pdfGeneratorProvider;

  public ToolsActivity_MembersInjector(Provider<FirebaseAuth> firebaseAuthProvider,
      Provider<PdfGenerator> pdfGeneratorProvider) {
    this.firebaseAuthProvider = firebaseAuthProvider;
    this.pdfGeneratorProvider = pdfGeneratorProvider;
  }

  public static MembersInjector<ToolsActivity> create(Provider<FirebaseAuth> firebaseAuthProvider,
      Provider<PdfGenerator> pdfGeneratorProvider) {
    return new ToolsActivity_MembersInjector(firebaseAuthProvider, pdfGeneratorProvider);
  }

  @Override
  public void injectMembers(ToolsActivity instance) {
    injectFirebaseAuth(instance, firebaseAuthProvider.get());
    injectPdfGenerator(instance, pdfGeneratorProvider.get());
  }

  @InjectedFieldSignature("com.healthtracker.app.ui.tools.ToolsActivity.firebaseAuth")
  public static void injectFirebaseAuth(ToolsActivity instance, FirebaseAuth firebaseAuth) {
    instance.firebaseAuth = firebaseAuth;
  }

  @InjectedFieldSignature("com.healthtracker.app.ui.tools.ToolsActivity.pdfGenerator")
  public static void injectPdfGenerator(ToolsActivity instance, PdfGenerator pdfGenerator) {
    instance.pdfGenerator = pdfGenerator;
  }
}
