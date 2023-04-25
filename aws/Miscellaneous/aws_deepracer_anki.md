## AWS Deepracer - for whom?

* Reinforcement learning tech - platform
* Do you want to create a machine learning model that makes inferences for a fictitious scenario just doesn't cut it in terms of interest factor.
* Maybe you're just keen to experiment with reinforcement learning tech in a global community context where you can share ideas.

## AWS Deepracer - Overview?

* AWS DeepRacer is a new global racing league for autonomous handheld sized racing cars.
* Build and train a reinforcement learning model that can be uploaded into an autonomous handled sized car.
* The RL model is then used in conjunction with the onboard camera, gyroscope, and accelerometer sensors to guide it around a racing track as quickly as possible.
* fastest willll be at the top of the leader board.
* Simulated vs real evnts
*  Anyone can purchase their own AWS DeepRacer to autonomous car, which is being sold through amazon.com The car is a 1:18th scale vehicle which has a camera at the front as its main guiding sensor.
* Sensors of the cars
  * CPU Intel Atom - 4GB RAM
  * 32GB wifi, 4MP Camera
  * Ubuntu OS 18 or 16
  * Accelerometer and gyroscope

## How to win deepracer?

* Building and training your Reinforcement Models, that uses a supported RL framework, algorithm, reward function, and other hyperparameters.
* The reward function is central to the entire outcome and is something you must develop and invest time in correcting and optimizing.
* The reward function will ultimately determine how fast your autonomous car can navigate the course correctly,
* Reward function is implemented as a Python based script, and the logic needs to consider many input variables,
* Parameters
  * The X and Y car location coordinates
  * On or off the track,
  * Displacement from the center line
  * The car orientation
  * The percentage of track completed
  * The number of steps completed
  * The speed of the car
  * and The steering position.