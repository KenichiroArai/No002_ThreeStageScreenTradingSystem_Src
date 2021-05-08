/**
 * ＫＭＧ．ツール
 */
package kmg.tool;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import kmg.tool.ssts.infrastructure.type.KmgString;
import kmg.tool.ssts.infrastructure.types.DelimiterTypes;

/**
 * アクセサ作成ツール
 *
 * @author KenichiroArai
 */
public class AccessorCreationlTool {

    /** 基準パス */
    private static final Path BASE_PATH = Paths.get(String.format("src/main/resources/tool/io")); //$NON-NLS-1$

    /** テンプレートファイルパス */
    private static final Path TEMPLATE_PATH = Paths.get(AccessorCreationlTool.BASE_PATH.toString(),
        "template/accessorCreationlTool.txt"); //$NON-NLS-1$

    /** 入力ファイルパス */
    private static final Path INPUT_PATH = Paths.get(AccessorCreationlTool.BASE_PATH.toString(), "input.txt"); //$NON-NLS-1$

    /** 出力ファイルパス */
    private static final Path OUTPUT_PATH = Paths.get(AccessorCreationlTool.BASE_PATH.toString(), "output.txt"); //$NON-NLS-1$

    /**
     * 走る
     *
     * @return TRUE：成功、FLASE：失敗
     * @throws FileNotFoundException
     *                               ファイルが存在しない例外
     * @throws IOException
     *                               入出力例外
     */
    @SuppressWarnings("static-method")
    public Boolean run() throws FileNotFoundException, IOException {

        final Boolean result = Boolean.FALSE;

        /* テンプレートの取得 */
        String template = null;
        try {

            template = Files.readAllLines(AccessorCreationlTool.TEMPLATE_PATH).stream()
                .collect(Collectors.joining(DelimiterTypes.LINE_SEPARATOR.getValue()));

        } catch (final FileNotFoundException e) {
            throw e;
        } catch (final IOException e) {
            throw e;
        }

        /* 入力から出力の処理 */
        try (final BufferedReader brInput = Files.newBufferedReader(AccessorCreationlTool.INPUT_PATH);
            final BufferedWriter bw = Files.newBufferedWriter(AccessorCreationlTool.OUTPUT_PATH);) {

            String output = template;
            String line   = null;
            while ((line = brInput.readLine()) != null) {
                line = line.replace("final", KmgString.EMPTY);
                line = line.replace("static", KmgString.EMPTY);
                final Pattern patternComment = Pattern.compile("/\\*\\* (\\S+)"); //$NON-NLS-1$
                final Matcher matcherComment = patternComment.matcher(line);
                if (matcherComment.find()) {
                    output = output.replace("$name", matcherComment.group(1)); //$NON-NLS-1$
                    continue;
                }

                final Pattern patternSrc = Pattern.compile("private\\s+((\\w|\\[\\])+)\\s+(\\w+);"); //$NON-NLS-1$
                final Matcher matcherSrc = patternSrc.matcher(line);
                if (matcherSrc.find()) {
                    output = output.replace("$type", matcherSrc.group(1)); //$NON-NLS-1$
                    output = output.replace("$item", matcherSrc.group(3)); //$NON-NLS-1$
                    output = output.replace("$Item", //$NON-NLS-1$
                        matcherSrc.group(3).substring(0, 1).toUpperCase() + matcherSrc.group(3).substring(1));
                    bw.write(output);
                    bw.write(System.lineSeparator());
                    output = template;
                }

            }

        } catch (final FileNotFoundException e) {
            throw e;
        } catch (final IOException e) {
            throw e;
        }

        return result;
    }

    /**
     * エントリポイント
     *
     * @param args
     *             オプション
     */
    public static void main(final String[] args) {

        final Class<AccessorCreationlTool> clasz = AccessorCreationlTool.class;
        try {
            final AccessorCreationlTool main = new AccessorCreationlTool();
            if (main.run()) {
                System.out.println(String.format("%s：失敗", clasz.toString())); //$NON-NLS-1$
            }
        } catch (final Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println(String.format("%s：成功", clasz.toString())); //$NON-NLS-1$
            System.out.println(String.format("%s：終了", clasz.toString())); //$NON-NLS-1$
        }

    }

}
