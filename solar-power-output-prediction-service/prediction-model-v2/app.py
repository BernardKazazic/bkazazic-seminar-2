import uvicorn
from fastapi import FastAPI
from fastapi import HTTPException
from pydantic import BaseModel
from keras.models import load_model
from sklearn.preprocessing import MinMaxScaler
import numpy as np
import pandas as pd


class PredictionRequest(BaseModel):
    time_ms: str
    temperature_celsius: float


def main():
    # Load the LSTM model
    model = load_model('C:/Workspace/lstm-model/lstm-model.h5')

    # Create the FastAPI app
    app = FastAPI()

    # Prepare scaler
    df = pd.read_csv('C:/Workspace/lstm-model-data/91-Site_1A-Trina.csv')
    df['timestamp'] = pd.to_datetime(df['timestamp'])
    df['Time_Format'] = df['timestamp'].dt.strftime('%H:%M:%S')
    df['Time_Delta'] = pd.to_timedelta(df['Time_Format'])
    df['Time_Ms'] = df['Time_Delta'].dt.total_seconds() * 1000
    df_cols = ['Time_Ms', 'Weather_Temperature_Celsius', 'Active_Power']
    df = df[df_cols]
    df = df.dropna()
    X = df[['Time_Ms', 'Weather_Temperature_Celsius']].values
    y = df['Active_Power'].values
    scaler_X = MinMaxScaler(feature_range=(0, 1))
    scaler_X.fit_transform(X)

    scaler_y = MinMaxScaler(feature_range=(0, 1))
    scaler_y.fit_transform(y.reshape(-1, 1))

    @app.post('/predict')
    def predict_power(data: PredictionRequest):
        try:
            # Map time_ms to correct format
            time = pd.to_datetime(data.time_ms)
            time = time.strftime('%H:%M:%S')
            time = pd.to_timedelta(time)
            time = time.total_seconds() * 1000

            # Reshape the input data
            X = np.array([[time, data.temperature_celsius]])
            print(X)
            X_scaled = scaler_X.transform(X)
            print(X_scaled)
            X_scaled = X_scaled.reshape((1, 1, 2))

            # Make predictions on the scaled data
            prediction_scaled = model.predict(X_scaled)

            # Rescale the prediction back to the original range
            prediction_rescaled = scaler_y.inverse_transform(prediction_scaled)
            prediction_rescaled = prediction_rescaled.flatten()

            # Return the prediction
            return {'active_power': prediction_rescaled[0].item()}
        except:
            raise HTTPException(status_code=500, detail='Prediction failed.')

    uvicorn.run(app, host='0.0.0.0', port=8000)


if __name__ == '__main__':
    main()
