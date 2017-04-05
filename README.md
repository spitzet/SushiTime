# SushiTime

Version 4.0

In this version of the game, the net is used as a window through which
to see the world. Also, objects are now mapped using local coordinates.
World coordinates and local coordinates are now separate. Instead of
drawing objects directly using world coordinates, affine transforms
are used to translate, rotate, and scale objects. In addition, a
screen transform is used so that the world is now right side up as
intended. Since the inside of the net is now all that the player can
see, expanding and contracting the net now zooms out and zooms in
respectively. the expand and contract commands have been bound to
the mouse wheel for ease of use. Also, panning the net now moves the
view of the world, and this has been bound to mouse dragging for
better playability. In order to translate, scale, and rotate all
game objects, GameObject now implements the ITransformable interface,
which requires all GameObjects to know how to translate, scale, and
rotate themselves, as well as clearing all transforms.

Since objects are in local coordinates, hierarchal objects can now
be implemented. Complex objects are made using more simple objects,
and body parts can be rotated then translated from the body's coordinate
system to the correct spot. This version uses this ability to make a
more detailed shark.

Lastly, a net is flashed on the screen for a moment when the user
invokes the scoop command. The net is comprised of 4 cubic bezier curves
with 4 control points each.


Version 3.0

In this version, the console is never printed to anymore. A
timer has been added to the game with Game itself as a listener.
Each time timer generates an event (using the delay of 20 milliseconds)
the game increments 20 milliseconds in time by invoking the tick command.
The tick command updates timer, moves the GameObjects, and checks
for collisions between GameObjects. Objects are now moved 50 times a
second because of the timer generating events every 20ms, and because
of this, they are moved by 1/50th of what they were moved in version 2.0.
The tick command invokes the tick method in GameWorld and passes the
elapsed time since the last action event. GameWorld then iterates
through the GameObjects and calls move(elapsed). The objects then
use this elapsed time to move this fraction of time that has passed, as
opposed to an entire second. Once the GameObjects have all moved, they
then check for collisions with other GameObjects using the new
ICollider interface. If a collision is detected, each object handles
the collision to the best of its ability, since it cannot delete
itself from the world. The GameObjects return a flag indicating what
is to be done in the game world, as well as internally modifying
themselves where necessary.

Objects in this version of the game are displayed graphically
in the map view. All objects know how to draw themselves so that the
map view can iterate through all GameObjects and polymorphically invoke
their draw method by overriding the PaintComponent method. Once the timer ticks,
mv.repaint() is called, and thereby invoking PaintComponent,  so that each
change in their position is redisplayed every 20ms. The effect of
this is that the objects look like they are moving, or if they have been
removed, they are no longer displayed on the screen. Also, if a fish eats,
it will grow larger in size.

Another feature added to this version of the game is that now sound effects
and a background music have been added. If certain objects collide, sound
effects are played. However, if the sound flag inside GameWorld is false,
no sounds will be heard, including the bgm. GameWorld handles the sound
effects, and they are played once a collision has been detected. Game
initially begins the bgm loop.

The game can now also be paused. Once paused, all sounds are muted, even
if the sound flag is true. Once unpaused, sound will resume only if
the sound flag is on. the Pause and Sound commands both must check to
make sure sound isn't playing when it shouldn't be, and sound is playing
when it should be. In addition, if the pause command is invoked, all
commands that are used to play the game are now disabled.

Lastly, the game now has a reverse command. Besides the play command,
this is the only command that is enabled when the game is paused.
Fish can now be selected, and if the reverse command is invoked
while these fish are selected, they will begin to move in the opposite
direction. When the game is unpaused, the reverse command is disabled,
and fish objects are not selectable. 

Version 2.0

This is the primary class which represents and executes control
for the game once it is instantiated. In this version of the
game, Game is now the main JFrame for the GUI, and contains not
only the GameWorld, but also the MapView, the PointsView, and
the ButtonPanel. In addition, this version of the game now uses
the Model-View-Controller architecture. Game is the controller,
which creates the model (GameWorld) and the views (MapView and
PointsView), and then registers the views as observers of
GameWorld. Game also creates commands to be used in the command
pattern and assigns them to the GUI. Instead of Game invoking
methods in the model directly, the GUI components that the
commands have been assigned to now invoke GameWorld's methods.
The views invoke the methods in GameWorld that have been granted
access to them as well.

Once Game is created by Starter, the user must enter the amount
of fish and food to be used in the game, then the setting up of
the GUI begins after the GameWorld is created. The frame is set
up and a layout manager is added so that the views appear
correctly on the screen. Next, the views and button panel are
created and added to the frame at the appropriate positions on
the screen. Buttons are then created and added to the ButtonPanel.
Commands are created and added to the buttons, and the menu is
built. Menu items are created directly by adding the commands
into the different menus. Finally, keystrokes are added to the
input map of the MapView, and commands are added to MapView's
action map. By doing this, key bindings are created which
invoke commands automatically when a key is pressed, without
needing to hit the enter key. Once everything has been set up,
the frame is made visible so that it can be seen on the screen.

In the previous version of the game, the getCommand() method would
accept user input into the console and then execute a method in
GameWorld directly. In this version, each method which manipulates
data in GameWorld's collection has now been made into a class to
be used in the command pattern. Game creates each command class
and passes the GameWorld object to it. Next, Game assigns these
commands to the appropriate GUI components using the setAction
method of the GUI components and passes the command objects,
which are all of type AbstractAction by extension. setAction
takes an AbstractAction object and automatically makes the
AbstractAction object passed to it a listener of the invoking
GUI component. By doing this, when the GUI component is clicked,
the actionPerformed method in the command object that was set
as a listener is invoked. In a similar fashion, commands are
also added to the key bindings so that when a key is pressed,
the command is invoked. When a command object is created, the
constructor calls AbstractAction's constructor with the name
that is to be displayed on the component. If GameWorld is
passed to the command object because it invokes a method in
GameWorld, the GameWorld object is assigned so that the appropriate
method can be called in the command object's actionPerformed
method.
