package br.com.marcosmilitao.idativosandroid.helper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import br.com.marcosmilitao.idativosandroid.Act_Cadastro_dados;
import br.com.marcosmilitao.idativosandroid.Act_Imagem;
import br.com.marcosmilitao.idativosandroid.R;

/**
 * Created by Idutto07 on 02/05/2017.
 */

public class ImageHelper {



    private ImageView imagemModelo;

    private Button botaoFoto;

    public ImageHelper(Act_Imagem activity) {

        this.imagemModelo = (ImageView) activity.findViewById(R.id.imagemModelo);

   }

    public ImageHelper(Act_Cadastro_dados activity) {
        this.imagemModelo = (ImageView) activity.findViewById(R.id.imagemModelo2);
    }

    //
    public Button getBotaoFoto(){
        return botaoFoto;
    }


    //Função para comprimir imagens. Alterar maxWidth e maxHeight para aumentar tamanho limite de altura e largura
    public  Bitmap ComprimirBitmap(String imagePath) {
        float maxHeight = 332.0f;
        float maxWidth = 443.0f;
        Bitmap scaledBitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(imagePath, options);

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;
        float imgRatio = (float) actualWidth / (float) actualHeight;
        float maxRatio = maxWidth / maxHeight;

        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;

            }
        }

        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);
        options.inJustDecodeBounds = false;
        options.inDither = false;
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];

        try {
            bmp = BitmapFactory.decodeFile(imagePath, options);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();

        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }

        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

        ExifInterface exif = null;
        try {
            exif = new ExifInterface(imagePath);
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
            } else if (orientation == 3) {
                matrix.postRotate(180);
            } else if (orientation == 8) {
                matrix.postRotate(270);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 85, out);

        byte[] byteArray = out.toByteArray();

        Bitmap updatedBitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

        return updatedBitmap;
    }

    private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;

        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }
        return inSampleSize;
    }

    private String criarArquivoComprimido(Bitmap imagemFoto, String caminho){
        String novoLocalArquivoFoto = null;

        if (caminho.toLowerCase().contains(".jpg")){
            novoLocalArquivoFoto = caminho.replace(".jpg", "cmp.jpg");
        } else if (caminho.toLowerCase().contains(".png")){
            novoLocalArquivoFoto = caminho.replace(".png", "cmp.png");
        }

        try{
            File file = new File(novoLocalArquivoFoto);
            FileOutputStream os = new FileOutputStream(file);
            imagemFoto.compress(Bitmap.CompressFormat.JPEG, 50, os);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return novoLocalArquivoFoto;
    }

    public void carregaImagem(String localArquivoFoto) {
        if(localArquivoFoto != null) {
            Bitmap imagemFoto = ComprimirBitmap(localArquivoFoto);
            imagemModelo.setImageBitmap(imagemFoto);
            String localArquivoFotoComprimida = criarArquivoComprimido(imagemFoto, localArquivoFoto);
            imagemModelo.setTag(localArquivoFotoComprimida);

        }
    }
}
