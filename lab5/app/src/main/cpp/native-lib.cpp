#include <jni.h>
#include <string>
#include <algorithm>

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_myapplication_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

extern "C" JNIEXPORT void JNICALL
Java_com_example_myapplication_MainActivity_sortArray(
        JNIEnv* env,
        jobject /* this */,
        jintArray array){

    jsize length = env->GetArrayLength(array);

    jint* arr = env->GetIntArrayElements(array, nullptr);

    std::sort(arr, arr + length, std::greater<int>());

    env->ReleaseIntArrayElements(array, arr, 0);
}