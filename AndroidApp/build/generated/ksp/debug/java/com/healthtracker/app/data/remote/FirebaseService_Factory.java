package com.healthtracker.app.data.remote;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class FirebaseService_Factory implements Factory<FirebaseService> {
  private final Provider<FirebaseAuth> authProvider;

  private final Provider<FirebaseFirestore> firestoreProvider;

  private final Provider<FirebaseStorage> storageProvider;

  public FirebaseService_Factory(Provider<FirebaseAuth> authProvider,
      Provider<FirebaseFirestore> firestoreProvider, Provider<FirebaseStorage> storageProvider) {
    this.authProvider = authProvider;
    this.firestoreProvider = firestoreProvider;
    this.storageProvider = storageProvider;
  }

  @Override
  public FirebaseService get() {
    return newInstance(authProvider.get(), firestoreProvider.get(), storageProvider.get());
  }

  public static FirebaseService_Factory create(Provider<FirebaseAuth> authProvider,
      Provider<FirebaseFirestore> firestoreProvider, Provider<FirebaseStorage> storageProvider) {
    return new FirebaseService_Factory(authProvider, firestoreProvider, storageProvider);
  }

  public static FirebaseService newInstance(FirebaseAuth auth, FirebaseFirestore firestore,
      FirebaseStorage storage) {
    return new FirebaseService(auth, firestore, storage);
  }
}
