package clojure;

import java.io.File;

import clojure.lang.Compiler;
import clojure.lang.IPersistentMap;
import clojure.lang.IPersistentSet;
import clojure.lang.ISeq;
import clojure.lang.RT;
import clojure.lang.Var;

public class Demo {

  public static void main(String[] args) throws Exception {
    if (true) {
      // RT.forceClass = true;
      RT.doInit();
      RT.load("clojure/test_clojure/agents");

      long d = System.currentTimeMillis();
      RT.var("clojure.test", "run-all-tests").invoke();

      // IFn p = (IFn) RT.var("clojure.core", "println").getRawRoot();
      // p.invoke("Hello world");

      // IFn reduce = (IFn) RT.var("clojure.core", "reduce").getRawRoot();
      // IFn map = (IFn) RT.var("clojure.core", "map").getRawRoot();
      // IFn plus = (IFn) RT.var("clojure.core", "+").getRawRoot();
      // IFn inc = (IFn) RT.var("clojure.core", "inc").getRawRoot();
      // System.out.println(reduce.invoke(plus, map.invoke(inc, RT.vector(1, 2,
      // 3))));

      System.out.println(System.currentTimeMillis() - d);
    } else {
      try {
        deleteDirectory(new File("target/src"));
        new File("target/src").mkdir();
        RT.doInit();
        RT.var("clojure.core", "load").invoke("test_clojure/agents");
        Var.pushThreadBindings(RT.mapUniqueKeys(
            clojure.lang.Compiler.COMPILE_PATH, "target/gen",
            clojure.lang.Compiler.SOURCE_GEN_PATH, "target/src",
            clojure.lang.Compiler.COMPILE_FILES, Boolean.TRUE));
        Compiler.RUNTIME.bindRoot(Boolean.TRUE);
        IPersistentMap options = (IPersistentMap) Compiler.COMPILER_OPTIONS
            .deref();
        RT.WARN_ON_REFLECTION.bindRoot(RT.T);
        Compiler.COMPILER_OPTIONS
            .bindRoot(RT.assoc(
                options,
                Compiler.elideMetaKey,
                RT.readString("[:doc :file :line :added :static :column :arglists :dynamic :private]")));
        RT.doInit();

        IPersistentSet loaded = (IPersistentSet) RT.var("clojure.core",
            "loaded-libs").invoke();
        for (ISeq i = loaded.seq(); i != null; i = i.next()) {
          RT.load(i.first().toString().replaceAll("[.]", "/")
              .replaceAll("-", "_"));
        }
        // RT.load("clojure/test_clojure/agents");

        // RT.load("clojure/test_clojure/api");
        // RT.load("clojure/test_clojure/atoms");
        // RT.load("clojure/test_clojure/clojure_set");
        // RT.load("clojure/test_clojure/clojure_walk");
        // RT.load("clojure/test_clojure/clojure_xml");
        // RT.load("clojure/test_clojure/clojure_zip");
        // RT.load("clojure/test_clojure/control");
        // RT.load("clojure/test_clojure/delays");
        // RT.load("clojure/test_clojure/for");
        // RT.load("clojure/test_clojure/keywords");
        // RT.load("clojure/test_clojure/logic");
        // RT.load("clojure/test_clojure/parallel");
        // RT.load("clojure/test_clojure/predicates");
        // RT.load("clojure/test_clojure/string");
        // RT.load("clojure/test_clojure/fn");
        // RT.load("clojure/test_clojure/data_structures");
        // RT.load("clojure/test_clojure/data");
        // RT.load("clojure/test_clojure/numbers");
        // RT.load("clojure/test_clojure/other_functions");

        // RT.load("clojure/test_clojure/reducers");
        // RT.load("clojure/test_clojure/sequences");
        // RT.load("clojure/test_clojure/special");
        // RT.load("clojure/test_clojure/vars");
        // RT.load("clojure/test_clojure/refs");
        // RT.load("clojure/test_clojure/transients");

        // RT.load("clojure/test_clojure/vectors");
        // RT.load("clojure/test_clojure/def");
        // RT.load("clojure/test_clojure/pprint");
        // RT.load("clojure/test_clojure/annotations");

        Var.popThreadBindings();
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        deleteDirectory(new File("target/gen"));
        new File("target/gen").mkdir();
        System.exit(0);
      }
    }
  }

  public static boolean deleteDirectory(File directory) {
    if (directory.exists()) {
      File[] files = directory.listFiles();
      if (null != files) {
        for (int i = 0; i < files.length; i++) {
          if (files[i].isDirectory()) {
            deleteDirectory(files[i]);
          } else {
            files[i].delete();
          }
        }
      }
    }
    return (directory.delete());
  }
}