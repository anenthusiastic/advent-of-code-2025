using ysfcc_YusufEren_Day_1;
using System.Collections.Generic;

namespace ysfcc_YusufEren_Day_1;
public class D1_Solution
{
    public void Run()
    {
        //Console.WriteLine("Hello, World!");

        string textFilePath = "ysfcc_YusufEren_Day_1/D1_Input.txt";
        var filestream = new System.IO.FileStream(textFilePath,
                                                  System.IO.FileMode.Open,
                                                  System.IO.FileAccess.Read,
                                                  System.IO.FileShare.ReadWrite);
        var file = new System.IO.StreamReader(filestream, System.Text.Encoding.UTF8, true, 128);

        string lineOfText;
        //var startPosition = 50;
        var currentPosition = 50;
        var stepDirectionDataList = new List<StepDirectionData>();
        var zeroResultCounter = 0;
        var zeroHitCounter = 0;
        while ((lineOfText = file.ReadLine()) != null)
        {
            var data = new StepDirectionData();
            data.Direction = lineOfText[0].ToString();
            data.Steps = int.Parse(lineOfText.Substring(1));
            stepDirectionDataList.Add(data);
            //Do something with the lineOfText
            //Console.WriteLine(lineOfText);
        }

        foreach (var data in stepDirectionDataList)
        {
            //Console.WriteLine(data.Direction + " " + data.Steps);
            if (data.Direction == "R")
            {
                var zeroCountNextDataReuslt = jumpNextNumbers(ref currentPosition, data.Steps);
                zeroResultCounter += zeroCountNextDataReuslt.ZeroResultCounter;
                zeroHitCounter += zeroCountNextDataReuslt.ZeroHitCounter;
            }
            else if (data.Direction == "L")
            {
                var zeroCountPrevDataReuslt = jumpPreviousNumbers(ref currentPosition, data.Steps);
                zeroResultCounter += zeroCountPrevDataReuslt.ZeroResultCounter;
                zeroHitCounter += zeroCountPrevDataReuslt.ZeroHitCounter;
            }
        }

        Console.WriteLine("currentPosition: " + currentPosition);
        Console.WriteLine("zeroResultCounter: " + zeroResultCounter);
        Console.WriteLine("zeroHitCounter: " + zeroHitCounter);
    }

    ZeroCountData jumpNextNumbers(ref int currentPosition, int steps)
    {
        var localZeroHitCounter = 0;
        var localZeroResultCounter = 0;
        for (int i = 0; i < steps; i++)
        {
            if (currentPosition == 99)
            {
                currentPosition = 0;
                localZeroHitCounter++;
            }
            else
            {
                currentPosition++;
            }
        }
        
        if (currentPosition == 0)
        {
            localZeroResultCounter++;
            localZeroHitCounter--;
        }
        return new ZeroCountData { ZeroResultCounter = localZeroResultCounter, ZeroHitCounter = localZeroHitCounter };
    }

    ZeroCountData jumpPreviousNumbers(ref int currentPosition, int steps)
    {
        var localZeroHitCounter = 0;
        var localZeroResultCounter = 0;
        for (int i = 0; i < steps; i++)
        {
            if (currentPosition == 0)
            {
                currentPosition = 99;
            }
            else
            {
                currentPosition--;
                if (currentPosition == 0)
                {
                    localZeroHitCounter++;
                }
            }
        }
        
        if (currentPosition == 0)
        {
            localZeroResultCounter++;
            localZeroHitCounter--;
        }

        return new ZeroCountData { ZeroResultCounter = localZeroResultCounter, ZeroHitCounter = localZeroHitCounter };

    }
}
